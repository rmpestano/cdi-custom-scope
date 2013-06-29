/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package custom.scope.web;

import custom.scope.extension.MyScope;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;

/**
 *
 * @author rmpestano
 */
@Named
@MyScope
public class MyBean implements Serializable{
    
    private String attribute;
    private Logger log = Logger.getLogger(getClass().getSimpleName());

  

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    
    
    @PostConstruct
    public void create(){
        log.info("creating bean:"+this);
    }
    
    @PreDestroy
    public void destroy(){
        log.info("destroing bean:"+this);
    }
    
}
