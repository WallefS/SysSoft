package br.ueg.posse.soft.config;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.sql.DataSource;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import br.ueg.posse.soft.controller.AgendaController;
import br.ueg.posse.soft.controller.converter.CidadeConverter;
import br.ueg.posse.soft.controller.converter.EstadoConverter;
import br.ueg.posse.soft.controller.converter.GrupoConverter;
import br.ueg.posse.soft.thymeleaf.SoftDialect;

import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;

@Configuration
@ComponentScan(basePackageClasses = { AgendaController.class })
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableAsync
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}

	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());

		engine.addDialect(new LayoutDialect());
		engine.addDialect(new SoftDialect());
		engine.addDialect(new DataAttributeDialect());
		engine.addDialect(new SpringSecurityDialect());

		return engine;
	}

	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		
		conversionService.addConverter(new CidadeConverter());
		conversionService.addConverter(new EstadoConverter());
		conversionService.addConverter(new GrupoConverter());
		//conversionService.addConverter(new PacienteConverter());
		//conversionService.addConverter(new ExameConverter());

		
			// API de Datas do Java 8
		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatter.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
		dateTimeFormatter.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}

	@Bean
	public DomainClassConverter<FormattingConversionService> domainClassConverter() {
		return new DomainClassConverter<FormattingConversionService>(mvcConversionService());
	}
	
	
	 @Bean
	    public JasperReportsViewResolver getJasperReportsViewResolver(DataSource datasource) {

	        JasperReportsViewResolver resolver = new JasperReportsViewResolver();
	        resolver.setPrefix("classpath:reports/");
	        resolver.setSuffix(".jasper");

	        //resolver.setReportDataKey("datasource");
			resolver.setJdbcDataSource(datasource);
	        resolver.setViewNames("*report_*");
	        resolver.setViewClass(JasperReportsMultiFormatView.class);
	        resolver.setOrder(0);
	        return resolver;
	    }
	
	
}
