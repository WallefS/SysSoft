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
import br.ueg.posse.soft.model.Exame;
import br.ueg.posse.soft.repository.Exames;
import br.ueg.posse.soft.repository.filter.ExameFilter;
import br.ueg.posse.soft.service.CadastroExameService;

@Controller
@RequestMapping("/exames")
public class ExameController {

	@Autowired
	private CadastroExameService cadastroExameService;
	
	@Autowired
	private Exames exames;

	@RequestMapping("/novo")
	public ModelAndView novo(Exame exame) {
		ModelAndView mv = new ModelAndView("/exame/CadastroExame");
		mv.addObject(new Exame());
		return mv;
	}

	// Salvar
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String salvar(@Validated Exame exame, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return "/exame/CadastroExame";
		}

		try {
			cadastroExameService.salvar(exame);
			attributes.addFlashAttribute("mensagem", "Exame Salvo com sucesso!!");
			return "redirect:/exames/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return "/exame/CadastroExame";
		}
	}

	// Pesquisar
	@GetMapping("/pesquisar")
	public ModelAndView pesquisar(ExameFilter exameFilter, BindingResult result
			, @PageableDefault(size = 15) Pageable pageable, HttpServletRequest httpServletRequest, HttpSession session) {
		ModelAndView mv = new ModelAndView("/exame/PesquisaExames");
		mv.addObject("exames", exames.findAll());
		
		PageWrapper<Exame> paginaWrapper = new PageWrapper<>(exames.filtrar(exameFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	// Editar
	@GetMapping("/{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Exame exame) {
		ModelAndView mv = new ModelAndView("/exame/CadastroExame");
		mv.addObject(exame);
		return mv;
	}

	// Excluir
	@DeleteMapping("/{codigo}")
		public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Long codigo){
		cadastroExameService.excluir(codigo);
		return ResponseEntity.ok().build();
	}

}
