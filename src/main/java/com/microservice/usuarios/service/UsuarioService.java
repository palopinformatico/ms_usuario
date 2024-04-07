package com.microservice.usuarios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.http.ResponseEntity;

import com.microservice.usuarios.model.domain.Phone;
import com.microservice.usuarios.model.domain.User;
import com.microservice.usuarios.model.dto.UserDto;
import com.microservice.usuarios.utils.Utils;

public class UsuarioService {

	public static String createUser(UserDto userDto) {
    	
        String mensajeSalida = "";
        
        //Valida formato email correcto
        String email = userDto.getEmail();
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]+)?$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
        	mensajeSalida = "{\"mensaje\":\"400 - La dirección de correo electrónico no es válida.\"}";
        	System.out.println("La dirección de correo electrónico no es válida.");
        } else {
	        
		    EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
		    EntityManager em = emf.createEntityManager();

            //Valida si correo existe previamente
            Query query = em.createQuery("SELECT u.email FROM User u WHERE u.email = :email");
            query.setParameter("email", userDto.getEmail());
            System.out.println("DVV");
            if(query.getResultList().size() != 0) {
            	mensajeSalida = "{\"mensaje\":\"400 - El correo ya registrado\"}";
            	System.out.println("El correo ya registrado");
            }
            else {
    	        // Iniciar transacción
    	        em.getTransaction().begin();
    			System.out.println(userDto.getName());
    			System.out.println(userDto.getEmail());
    			System.out.println(userDto.getPassword());
    			
    			for (Phone phone : userDto.getPhones()) {
    	            System.out.println(phone.getId());
    	            System.out.println(phone.getNumber());
    	            System.out.println(phone.getCitycode());
    	            System.out.println(phone.getContrycode());
    	        }
    	
    	        // Crear una nueva entidad y guardarla en la base de datos
    	        User user = new User();
    	        user.setId(Utils.generateRandomUUID());
    	        user.setName(userDto.getName());
    	        user.setEmail(userDto.getEmail());
    	        user.setPassword(userDto.getPassword());
    	        user.setCreated(Utils.getCurrentDateTime());
    	        user.setModified(Utils.getCurrentDateTime());
    	        user.setLast_login(Utils.getCurrentDateTime());
    	        user.setToken(Utils.generateRandomUUID());
    	        user.setIsactive("Y");

				List<Long> listaPhones = new ArrayList<Long>();
    			for (Phone phoneDto : userDto.getPhones()) {
    				Phone phone = new Phone();
    				phone.setNumber(phoneDto.getNumber());
    				phone.setCitycode(phoneDto.getCitycode());
    				phone.setContrycode(phoneDto.getContrycode());
    		        em.persist(phone);
    		        listaPhones.add(phone.getId());
    	        }
    			user.setPhones(listaPhones);
    	        em.persist(user);
    	
    	        // Commit de la transacción
    	        em.getTransaction().commit();
    	        
    	        // Recuperar la entidad de la base de datos y verificar si se ha persistido correctamente
    	        User userAux = em.find(User.class, user.getId());
    	        if (userAux != null) {
    	            System.out.println(userAux.getId());
    	            System.out.println("La entidad se ha persistido correctamente.");
    	        } else {
    	            System.out.println("No se ha podido persistir la entidad.");
    	        }
    	        String mensaje = "{\"id\":\"" + user.getId() + "\",";
    	        mensaje = mensaje  + "\"nombre\":\"" + user.getName() + "\",";
    	        mensaje = mensaje  + "\"email\":\"" + user.getEmail() + "\",";
    	        mensaje = mensaje  + "\"password\":\"" + user.getPassword() + "\",";
    	        
    	        String phones = "";
    			for(Long phoneId : user.getPhones()) {
    				Phone phone = em.find(Phone.class, phoneId);
    	            System.out.println(phone.getId());
    	            System.out.println(phone.getNumber());
    	            System.out.println(phone.getCitycode());
    	            System.out.println(phone.getContrycode());
    	            phones = phones + phone.getNumber() + " - " + phone.getCitycode() + " - " + phone.getContrycode() + ";";
    			}
    	        
    	        mensaje = mensaje  + "\"phones\":\"" + phones + "\",";
    	        mensaje = mensaje  + "\"id\":\"" + user.getId() + "\",";
    	        mensaje = mensaje  + "\"created\":\"" + user.getCreated() + "\",";
    	        mensaje = mensaje  + "\"modified\":\"" + user.getModified() + "\",";
    	        mensaje = mensaje  + "\"last_login\":\"" + user.getLast_login() + "\",";
    	        mensaje = mensaje  + "\"token\":\"" + user.getToken() + "\",";
    	        mensaje = mensaje  + "\"isActive\":\"" + user.getIsactive() + "\"}";
    	        mensajeSalida = "{\"mensaje\":\"200 - Usuario registrado - " + mensaje + "\"}";
            }

            // Cerrar EntityManager y EntityManagerFactory
            em.close();
            emf.close();
        }
        
        return mensajeSalida;
	}

	public static String findUser(User usuario) {
    	
        String mensajeSalida = "";

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        
		// Iniciar transacción
		em.getTransaction().begin();
	
		// Recuperar la entidad de la base de datos y verificar si se ha persistido correctamente
		User userAux = em.find(User.class, usuario.getId());
		if (userAux != null) {
			System.out.println(userAux.getName());
			System.out.println(userAux.getEmail());
			System.out.println(userAux.getPassword());
			System.out.println(userAux.getCreated());
			System.out.println(userAux.getModified());
			System.out.println(userAux.getLast_login());
			System.out.println(userAux.getToken());
			System.out.println(userAux.getIsactive());

			for(Long phoneId : userAux.getPhones()) {
				Phone phone = em.find(Phone.class, phoneId);
	            System.out.println(phone.getId());
	            System.out.println(phone.getNumber());
	            System.out.println(phone.getCitycode());
	            System.out.println(phone.getContrycode());
			}
	            
		    System.out.println("La entidad se ha persistido correctamente.");
		} else {
		    System.out.println("La entidad no existe.");
		}

        // Cerrar EntityManager y EntityManagerFactory
        em.close();
        emf.close();
        
        mensajeSalida = "{\"mensaje\":\"200 - mensaje trae\"}";
    	
        return mensajeSalida;

	}
}
