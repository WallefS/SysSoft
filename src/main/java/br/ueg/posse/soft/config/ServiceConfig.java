package br.ueg.posse.soft.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.ueg.posse.soft.service.CadastroAgendaService;

@Configuration
@ComponentScan(basePackageClasses = CadastroAgendaService.class)
public class ServiceConfig {

}
