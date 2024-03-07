package com.smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontact.dao.ContactRepository;
import com.smartcontact.dao.UserRepository;
import com.smartcontact.entities.Contact;
import com.smartcontact.entities.User;
import com.smartcontact.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ContactRepository contactRepository;
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal){
		System.out.println("USERNAME " + principal.getName());
		User user = userRepository.getUserbyUsername(principal.getName());
		model.addAttribute("user",user);
		System.out.println(user);
		
	}
	
	@RequestMapping("/dashboard")
   public String userdashboard(Model model,Principal principal) {
	   User user = this.userRepository.getUserbyUsername(principal.getName());
	   model.addAttribute("username1",user.getUsername());
	   model.addAttribute("usertotalcontact",contactRepository.getTotalContact(user.getUser_id()));
		
		
	   return "user/userdashboard";
	   
   }
	@RequestMapping(value = "/add-contact-form",method = RequestMethod.GET)
	public String addContactForm(Model model) {
		model.addAttribute("contact",new Contact());
		return "user/add_contact_form";
	}
	
	@RequestMapping(value = "/process-contact",method = RequestMethod.POST)
	public String processContact(@Valid @ModelAttribute Contact contact,BindingResult result,@RequestParam("contactimageurl")MultipartFile multi, 
			Principal principal,HttpSession session) {
        try {
        	
		User user = userRepository.getUserbyUsername(principal.getName());
        System.out.println(contact);
        
        if(multi.isEmpty()) {
        	System.out.println("File is Empty");
        	contact.setContactimageurl("default.png");
        }
        else {
        	contact.setContactimageurl(multi.getOriginalFilename());
        	File file = new ClassPathResource("static/image").getFile();
        	Path path = Paths.get(file.getAbsolutePath() + File.separator + multi.getOriginalFilename());
        	Files.copy(multi.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
        	System.out.println("File uploaded");
        }
        contact.setUser(user);
        user.getContacts().add(contact);
        this.userRepository.save(user);
        System.out.println("Added to Database");
        
        //success message
        
        session.setAttribute("message", new Message("Contact added Successfully","alert-success"));
        
        
        }
        catch(Exception e) {
        	e.printStackTrace();
        	session.setAttribute("message", new Message("Something went wrong !! Try again","alert-danger"));
        }
        return "user/add_contact_form";		
	}
	
	@GetMapping("/view-contact/{page}")
	public String viewContact(@PathVariable("page")Integer page, Model model,Principal principal,HttpSession session) {
		
		User user = this.userRepository.getUserbyUsername(principal.getName());
		Pageable p1 = PageRequest.of(page, 7);
		Page<Contact> contactlist = this.contactRepository.getContactByUserId(user.getUser_id(),p1);
		if(contactlist.isEmpty()) {
			session.setAttribute("message1",new Message("Your Contact list is Empty","alert-info"));
		}
        model.addAttribute("contactlist",contactlist);	
        model.addAttribute("currentpage",page);
        model.addAttribute("totalpages",contactlist.getTotalPages());
		return "user/view_contact";
	}
	
	@GetMapping("/delete/{contactid}")
	@Transactional
	public String deleteContact(@PathVariable("contactid")Integer contactid,Model model,HttpSession session,Principal principal) {
		Optional<Contact> contactoptional = this.contactRepository.findById(contactid);
		Contact contact =contactoptional.get();
		//contact.setUser(null);
		User user = this.userRepository.getUserbyUsername(principal.getName());
		user.getContacts().remove(contact);
		this.contactRepository.delete(contact);
		session.setAttribute("message",new Message("Contact deleted successfully","alert-success"));
		return "user/view_contact";
	}
	
	@PostMapping("/update/{contactid}")
	public String updateContact(@PathVariable("contactid")Integer contactId,Model model) {
		Contact contact = this.contactRepository.findById(contactId).get();
		model.addAttribute(contact);
		return "user/updatecontact";
	}
	
	@PostMapping("/process-update")
	public String updateprocess(@ModelAttribute Contact contact,@RequestParam("profilephoto")MultipartFile file,HttpSession session,Principal principal) {
		
		try {
			User user = this.userRepository.getUserbyUsername(principal.getName());
			Contact oldcontact = this.contactRepository.findById(contact.getContactid()).get();
			if(!file.isEmpty()) {
				File saveFile = new ClassPathResource("static/image").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator+ file.getOriginalFilename());
			    Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
			    contact.setContactimageurl(file.getOriginalFilename());
			}
			else {
				contact.setContactimageurl(oldcontact.getContactimageurl());
			}
			System.out.println(contact.getContactid() + " " +contact.getContactname());
			contact.setUser(user);
			this.contactRepository.save(contact);
		}
		
		
		catch(Exception e) {
			e.printStackTrace();
		}
		return "user/view_contact";
	}
	
	@GetMapping("/view-contact/sort-az/{page}")
	public String viewSortAZ(@PathVariable("page")Integer page,Model model,Principal principal) {
		User user = this.userRepository.getUserbyUsername(principal.getName());
		Pageable p1 = PageRequest.of(page, 7);
		Page<Contact> contactlist = this.contactRepository.getSortedAZContactByUserId(user.getUser_id(),p1);
		/*if(contactlist.isEmpty()) {
			session.setAttribute("message1",new Message("Your Contact list is Empty","alert-info"));
		}*/
        model.addAttribute("contactlist",contactlist);	
        model.addAttribute("currentpage",page);
        model.addAttribute("totalpages",contactlist.getTotalPages());
		return "user/view_contact";
		
	}
	
	@GetMapping("/view-contact/sort-za/{page}")
	public String viewSortZA(@PathVariable("page")Integer page,Model model,Principal principal) {
		User user = this.userRepository.getUserbyUsername(principal.getName());
		Pageable p1 = PageRequest.of(page, 7);
		Page<Contact> contactlist = this.contactRepository.getSortedZAContactByUserId(user.getUser_id(),p1);
		/*if(contactlist.isEmpty()) {
			session.setAttribute("message1",new Message("Your Contact list is Empty","alert-info"));
		}*/
        model.addAttribute("contactlist",contactlist);	
        model.addAttribute("currentpage",page);
        model.addAttribute("totalpages",contactlist.getTotalPages());
		return "user/view_contact";
		
	}
	
	@GetMapping("/myprofile")
	public String myProfile(Model model,Principal principal) {
		User user = this.userRepository.getUserbyUsername(principal.getName());
		model.addAttribute("user",user);
		return "user/profile";
	}
	

}
