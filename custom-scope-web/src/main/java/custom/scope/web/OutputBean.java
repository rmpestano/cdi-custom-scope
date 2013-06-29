/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package custom.scope.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author rmpestano
 */
@Named
@SessionScoped
public class OutputBean implements Serializable{
    
    @Inject 
    MyBean myBean;
    
    @Inject 
    MyBean2 myBean2;

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
    
    
}
