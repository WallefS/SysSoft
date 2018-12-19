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
import br.ueg.posse.soft.model.Empresa;
import br.ueg.posse.soft.repository.Empresas;
import br.ueg.posse.soft.repository.filter.EmpresaFilter;
import br.ueg.posse.soft.service.CadastroEmpresaService;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {
	
	@Autowired
	private CadastroEmpresaService cadastroEmpresaService;
	
	@Autowired
	private Empresas empresas;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("/empresa/CadastroEmpresa");
		mv.addObject(new Empresa());
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String salvar(@Validated Empresa empresa, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return "/empresa/CadastroEmpresa";
		}

		try {
			cadastroEmpresaService.salvar(empresa);
			attributes.addFlashAttribute("mensagem", "Empresa Salvo com sucesso!!");
			return "redirect:/empresas/novo";
		} catch (IllegalArgumentException e) {
			return "/empresa/CadastroEmpresa";
		}
	}

	@GetMapping("/pesquisar")
	public ModelAndView pesquisar(EmpresaFilter empresaFilter, BindingResult result
			, @PageableDefault(size = 15) Pageable pageable, HttpServletRequest httpServletRequest, HttpSession session) {
		ModelAndView mv = new ModelAndView("/empresa/PesquisaEmpresas");
		mv.addObject("empresas", empresas.findAll());
		
		PageWrapper<Empresa> paginaWrapper = new PageWrapper<>(empresas.filtrar(empresaFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView edicao(@PathVariable("id") Long id) {
		Empresa empresa = empresas.findOne(id); 
		ModelAndView mv = new ModelAndView("/empresa/CadastroEmpresa");
		mv.addObject(empresa);
		return mv;
	}

	@DeleteMapping("/{id}")
		public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Empresa empresa){
		cadastroEmpresaService.excluir(empresa);
		return ResponseEntity.ok().build();
	}


}
