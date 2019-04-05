package es.curso.registro.service;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;

import es.curso.registro.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UserServiceTest {
	
	@Autowired
	UserService userService;
    private static EmbeddedDatabase db;
    @BeforeClass
    public static void setUp() {
        //db = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.addScript("create-db.sql")
    		.addScript("insert-db.sql")
    		.build();
    }
    
    @Test
    public void findByEmailTest () {
    	User user = userService.findByEmail("jose.oubina@outlook.es");   	
    	Assert.assertTrue(user.getId() == 1);	
    }
    
    @Test
    public void findByEmailTestFailed () {
    	User user = userService.findByEmail("asdfasdfadfs@outlook.es");   	
    	Assert.assertNull(user);
    		
    }

}
