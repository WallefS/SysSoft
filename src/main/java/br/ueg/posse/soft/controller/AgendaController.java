package br.ueg.posse.soft.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ueg.posse.soft.controller.page.PageWrapper;
import br.ueg.posse.soft.model.Agenda;
import br.ueg.posse.soft.model.StatusAgenda;
import br.ueg.posse.soft.repository.Agendas;
import br.ueg.posse.soft.repository.Exames;
import br.ueg.posse.soft.repository.Medicos;
import br.ueg.posse.soft.repository.Pacientes;
import br.ueg.posse.soft.repository.filter.AgendaFilter;
import br.ueg.posse.soft.service.CadastroAgendaService;
import br.ueg.posse.soft.service.exception.HoraAgendaCadastradoException;
import br.ueg.posse.soft.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/agendas")
public class AgendaController {
	// Ioc
	@Autowired
	private CadastroAgendaService cadastroAgendaService;
	// Ioc
	@Autowired
	private Medicos medicos;
	// Ioc
	@Autowired
	private Pacientes pacientes;
	// Ioc
	@Autowired
	private Exames exames;

	@Autowired
	private Agendas agendas;

	@Autowired
	private CadastroAgendaService CadastroAgendaService;

	@RequestMapping("/novo")
	public ModelAndView novo(Agenda agenda) {
		ModelAndView mv = new ModelAndView("/agenda/CadastroAgenda");
		mv.addObject(new Agenda());
		// listando objetos vindo do banco de dados
		mv.addObject("medicos", medicos.findAll());
		mv.addObject("pacientes", pacientes.findAll());
		mv.addObject("exames", exames.findAll());
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String salvar(@Validated Agenda agenda, Errors errors,
			RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return "/agenda/CadastroAgenda";
		}

		try {
			cadastroAgendaService.salvar(agenda);
			attributes.addFlashAttribute("mensagem",
					"Agenda Salvo com sucesso!!");
			return "redirect:/agendas/novo";
		} catch (HoraAgendaCadastradoException e) {
			errors.rejectValue("hora", e.getMessage(), e.getMessage());
			return "/agenda/CadastroAgenda";
		}
	}

	@GetMapping("/{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Agenda agenda) {
		ModelAndView mv = new ModelAndView("/agenda/CadastroAgenda");
		mv.addObject("medicos", medicos.findAll());
		mv.addObject("pacientes", pacientes.findAll());
		mv.addObject("exames", exames.findAll());
		mv.addObject(agenda);
		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(
			@PathVariable("codigo") Long codigo) {
		try {
			cadastroAgendaService.excluir(codigo);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/pesquisar")
	public ModelAndView pesquisar(AgendaFilter agendaFilter,BindingResult result,@PageableDefault(size = 15) Pageable pageable,
			HttpServletRequest httpServletRequest, HttpSession session) {
		ModelAndView mv = new ModelAndView("/agenda/PesquisaAgendas");
		mv.addObject("agendas", agendas.findAll());

		PageWrapper<Agenda> paginaWrapper = new PageWrapper<>(agendas.filtrar(agendaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {

		return cadastroAgendaService.receber(codigo);
	}

	@ModelAttribute("todosStatusAgenda")
	public List<StatusAgenda> todosStatusAgenda() {
		return Arrays.asList(StatusAgenda.values());
	}

}
