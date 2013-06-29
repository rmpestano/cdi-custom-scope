/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package custom.scope.web;

import custom.scope.extension.MyScope;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.NormalScope;
import javax.inject.Named;

/**
 *
 * @author rmpestano
 */
@Named
@MyScope
public class MyBean2 implements Serializable{
    
    private String attribute;
  

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    
    
    @PostConstruct
    public void create(){
        System.out.println("creating bean:"+this);
    }
    
    @PreDestroy
    public void destroy(){
        System.out.println("destroing bean:"+this);
    }
    
}