package br.ueg.posse.soft.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ueg.posse.soft.controller.page.PageWrapper;
import br.ueg.posse.soft.model.Paciente;
import br.ueg.posse.soft.repository.Estados;
import br.ueg.posse.soft.repository.Pacientes;
import br.ueg.posse.soft.repository.filter.PacienteFilter;
import br.ueg.posse.soft.service.CadastroPacienteService;
import br.ueg.posse.soft.service.exception.PacienteAgendaCadastradoException;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	private CadastroPacienteService cadastroPacienteService;
	
	@Autowired 
	private Estados estados;
	
	@Autowired
	private Pacientes pacientes;
	

	@RequestMapping("/novo")
	public ModelAndView novo(Paciente paciente) {
		ModelAndView mv = new ModelAndView("/paciente/CadastroPaciente");
		mv.addObject("estados", estados.findAll());
		//mv.addObject("cidades", cidades.findAll());
		//mv.addObject(new Paciente());
		return mv;

	}
	// Salvar
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Paciente paciente, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			System.out.println(">>>>"+result);
			return novo(paciente);
			
		}
		try {
			cadastroPacienteService.salvar(paciente);
		} catch (PacienteAgendaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(paciente);
		}
		
		attributes.addFlashAttribute("mensagem", "Paciente salvo com sucesso!");
		return new ModelAndView("redirect:/pacientes/novo");
	}

	@GetMapping("/pesquisar")
	public ModelAndView pesquisar(PacienteFilter pacienteFilter, BindingResult result
			, @PageableDefault(size = 15) Pageable pageable, HttpServletRequest httpServletRequest, HttpSession session) {
		ModelAndView mv = new ModelAndView("/paciente/PesquisaPacientes");
		mv.addObject("pacientes", pacientes.findAll());
		
		PageWrapper<Paciente> paginaWrapper = new PageWrapper<>(pacientes.filtrar(pacienteFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Paciente paciente) {
		ModelAndView mv = new ModelAndView("/paciente/CadastroPaciente");
		mv.addObject(paciente);
		mv.addObject("estados", estados.findAll());
		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Paciente paciente){
	cadastroPacienteService.excluir(paciente);
	return ResponseEntity.ok().build();
	}

}
