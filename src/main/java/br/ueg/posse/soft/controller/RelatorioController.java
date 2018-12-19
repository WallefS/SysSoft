package br.ueg.posse.soft.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ueg.posse.soft.dto.PeriodoRelatorio;
import br.ueg.posse.soft.repository.Empresas;

@Controller
@RequestMapping("/relatorio")
public class RelatorioController {
	
	@Autowired
	private Empresas empresas;

	// Novo
	@GetMapping("/geral")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView("/relatorio/relatorios");
		return mv;
	}

	@GetMapping("/protocolosEmitidos")
	public ModelAndView relatorioProtocolosEmitidas() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioProtocolos");
		mv.addObject(new PeriodoRelatorio());
		return mv;
	}

	@PostMapping("/protocolosEmitidos")
	public ModelAndView gerarRelatorioProtocolosEmitidos(PeriodoRelatorio periodoRelatorio) {
		Map<String, Object> parametros = new HashMap<>();

		Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());

		parametros.put("format", "pdf");

		parametros.put("data_inicio", dataInicio);
		parametros.put("data_fim", dataFim);

		return new ModelAndView("report_protocolos_emitidas", parametros);
	}

	/*
	 * @GetMapping("/documentos") public ModelAndView relatorioDocumentos() {
	 * ModelAndView mv = new ModelAndView("relatorio/relatorios");
	 * mv.addObject(new PeriodoRelatorio()); return mv; }
	 * 
	 * @PostMapping("/documentos") public ModelAndView
	 * gerarRelatorioDocumentos() { Map<String, Object> parametros = new
	 * HashMap<>();
	 * 
	 * parametros.put("format", "pdf");
	 * 
	 * return new ModelAndView("report_documento", parametros); }
	 * 
	 * 
	 * @GetMapping("/departamentos") public ModelAndView
	 * relatorioDepartamentos() { ModelAndView mv = new
	 * ModelAndView("relatorio/relatorios"); mv.addObject(new
	 * PeriodoRelatorio()); return mv; }
	 * 
	 * @PostMapping("/departamentos") public ModelAndView
	 * gerarRelatorioDepartamentos() { Map<String, Object> parametros = new
	 * HashMap<>();
	 * 
	 * parametros.put("format", "pdf");
	 * 
	 * return new ModelAndView("report_departamento", parametros); }
	 * 
	 * @GetMapping("/empresas") public ModelAndView relatorioEmpresas() {
	 * ModelAndView mv = new ModelAndView("relatorio/relatorios");
	 * mv.addObject(new PeriodoRelatorio()); return mv; }
	 * 
	 * @PostMapping("/departamentos") public ModelAndView
	 * gerarRelatorioEmpresas() { Map<String, Object> parametros = new
	 * HashMap<>();
	 * 
	 * parametros.put("format", "pdf");
	 * 
	 * return new ModelAndView("report_empresa", parametros); }
	 */
}
