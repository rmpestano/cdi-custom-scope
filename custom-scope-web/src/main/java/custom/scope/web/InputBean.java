/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package custom.scope.web;

import custom.scope.extension.KillEvent;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author rmpestano
 */
@SessionScoped
@Named
public class InputBean implements Serializable{
    
    @Inject 
    MyBean myBean;
    
    @Inject 
    MyBean2 myBean2;
    
    @Inject 
    Event<KillEvent> killEvent;
    

    public MyBean getMyBean() {
        return myBean;
    }

    public void setMyBean(MyBean myBean) {
        this.myBean = myBean;
    }

    public MyBean2 getMyBean2() {
        return myBean2;
    }

    public void setMyBean2(MyBean2 myBean2) {
        this.myBean2 = myBean2;
    }
    
    
    public void killBean1(){
        killEvent.fire(new KillEvent(MyBean.class));
    }
    
     public void killBean2(){
        killEvent.fire(new KillEvent(MyBean2.class));
    }
}
