package br.ueg.posse.soft.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ueg.posse.soft.controller.page.PageWrapper;
import br.ueg.posse.soft.model.Medico;
import br.ueg.posse.soft.repository.Medicos;
import br.ueg.posse.soft.repository.filter.MedicoFilter;
import br.ueg.posse.soft.service.CadastroMedicoService;
import br.ueg.posse.soft.service.exception.ImpossivelExcluirEntidadeException;
import br.ueg.posse.soft.service.exception.MedicoAgendaCadastradoException;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private CadastroMedicoService cadastroMedicoService;

	@Autowired
	private Medicos medicos;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("/medico/CadastroMedico");
		mv.addObject(new Medico());
		return mv;

	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String salvar(@Validated Medico medico, Errors errors,
			RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return "/medico/CadastroMedico";

		}

		try {
			cadastroMedicoService.salvar(medico);
			attributes.addFlashAttribute("mensagem",
					"Medico Salvo com sucesso!!");
			return "redirect:/medicos/novo";
		} catch (MedicoAgendaCadastradoException e) {
			errors.rejectValue("nome", e.getMessage(), e.getMessage());
			return "/medico/CadastroMedico";
		}
	}

	@GetMapping("/pesquisar")
	public ModelAndView pesquisar(MedicoFilter medicoFilter,
			BindingResult result,
			@PageableDefault(size = 15) Pageable pageable,
			HttpServletRequest httpServletRequest, HttpSession session) {
		ModelAndView mv = new ModelAndView("/medico/PesquisaMedicos");
		mv.addObject("medicos", medicos.findAll());
		PageWrapper<Medico> paginaWrapper = new PageWrapper<>(medicos.filtrar(
				medicoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Medico medico) {
		ModelAndView mv = new ModelAndView("/medico/CadastroMedico");
		mv.addObject(medico);
		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long codigo) {
		try {
			cadastroMedicoService.excluir(codigo);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
