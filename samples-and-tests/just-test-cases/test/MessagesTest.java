
import play.*;

import org.junit.Test;

import play.test.UnitTest;
import play.i18n.Lang;
import play.i18n.Messages;

public class MessagesTest extends UnitTest {

    @Test
    public void testIncludeMessagesDefault() {
        Lang.set("");
        assertEquals("Username:", Messages.get("login.username"));  
        assertEquals("Hello MessagesTest you are log in.", Messages.get("login.success", "MessagesTest"));    
        assertEquals("Another message", Messages.get("another.message"));  
    }
    
    @Test
    public void testIncludeMessagesUndefinedLang() {
        assertEquals("Username:", Messages.getMessage("ru", "login.username"));  
        assertEquals("Hello MessagesTest you are log in.", Messages.getMessage("ru","login.success", "MessagesTest"));    
        assertEquals("Another message", Messages.getMessage("ru","another.message"));  
    }
    
    @Test
    public void testIncludeMessagesFrench() {
        try{
            Lang.set("fr");
            assertEquals("Nom d'utilisateur&nbsp;:", Messages.get("login.username"));  
            assertEquals("Bonjour MessagesTest vous êtes connecté.", Messages.get("login.success", "MessagesTest"));    
            assertEquals("Un autre message", Messages.get("another.message"));  
	}finally{
	    Lang.set("");
        }	  
    }

    @Test
    public void testCountrySpecificMessage() {
        try{
            Lang.set("fr_CA");
            assertEquals("Votre nom:", Messages.get("login.username"));
        }finally{
            Lang.set("");
        }
    }

    @Test
    public void testLanguageFallbackFrench() {
        try{
            Lang.set("fr");
            assertEquals("Your account has expired.", Messages.get("login.accountExpired")); // doesn't exist in french - fallback to default
        }finally{
            Lang.set("");
        }
    }

    @Test
    public void testLanguageFallbackFrenchCanadian() {
        try{
            Lang.set("fr_CA");
            assertEquals("Bonjour MessagesTest vous êtes connecté.", Messages.get("login.success", "MessagesTest")); // doesn't exist in french canadian - fallback to french
            assertEquals("Your account has expired.", Messages.get("login.accountExpired")); // doesn't exist in french and also not in french canadian - fallback to default
        }finally{
            Lang.set("");
        }
    }

    @Test
    public void testLanguageFallbackFrenchUndefinedCountry() {
        try{
            // using change() instead of set() to find closest match
            Lang.change("fr_XY"); // fr_XY was not configured in application.conf, but fr was and therefore falling back to fr
            assertEquals("fr", Lang.get());
            assertEquals("Bonjour MessagesTest vous êtes connecté.", Messages.get("login.success", "MessagesTest"));
        }finally{
            Lang.set("");
        }
    }
}