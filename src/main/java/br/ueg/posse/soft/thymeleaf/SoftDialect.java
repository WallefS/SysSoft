package br.ueg.posse.soft.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.ueg.posse.soft.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.ueg.posse.soft.thymeleaf.processor.MenuAttributeTagProcessor;
import br.ueg.posse.soft.thymeleaf.processor.MessageElementTagProcessor;
import br.ueg.posse.soft.thymeleaf.processor.OrderElementTagProcessor;
import br.ueg.posse.soft.thymeleaf.processor.PaginationElementTagProcessor;

public class SoftDialect extends AbstractProcessorDialect {

	public SoftDialect() {
		super("Soft", "soft", StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		return processadores;
	}

}
