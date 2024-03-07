package com.smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontact.dao.UserRepository;
import com.smartcontact.entities.User;
import com.smartcontact.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	
	
    
	@RequestMapping("/")
	public String test(Model model) {
		model.addAttribute("title","Home | Contact Hub");
		return "home";
	}
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("abouttitle","About | Contact Hub");
		return "about";
	}
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signuptitle","Sign up | Contact Hub");
		model.addAttribute("user",new User());
		return "signup";
	}
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,
			                   @RequestParam(value = "agreement",defaultValue = "false") boolean agreement,
			                   @RequestParam(value = "description")String description,
			                   Model model,HttpSession session) {
		try {
			if(!agreement) {
				System.out.println("You have not agreed terms and Conditions");
				throw new Exception("You have not agreed terms and Conditions");
			}
			if(result1.hasErrors()) {
				System.out.println("Error" + result1.toString());
				model.addAttribute("user",user);
				//return "signup";
			}
			else {
				user.setRole("ROLE_USER");
				user.setEnabled(true);
				user.setUserpassword(passwordEncoder.encode(user.getUserpassword()));
				System.out.println(user);
				System.out.println(agreement);
				System.out.println(description);
				User result = this.userRepository.save(user);
				System.out.println(result);
				model.addAttribute("user",user);
				session.setAttribute("message",new Message("Sucessfully Registered","alert-success"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something went wrong...!!" + e.getMessage(),"alert-danger"));
		}
		
		return "signup";
	}
	@RequestMapping("/login")
	public String login() {
		
		return "login";
	}
	
	
	

}
