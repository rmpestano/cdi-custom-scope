package custom.scope.extension;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Bean;

/**
 *
 * @author rmpestano
 */
public class CustomScopeContext implements Context, Serializable {

    //moved to CustomScopeContextHolder
//    private static Map<Class, Object> customScopeContextHolder.getBeans() = Collections.synchronizedMap(new HashMap<Class, Object>());

    private CustomScopeContextHolder customScopeContextHolder;
    
    public CustomScopeContext() {
        System.out.println("Init");
        this.customScopeContextHolder = CustomScopeContextHolder.getInstance();
    }

    @Override
    public <T> T get(final Contextual<T> contextual) {
        Bean bean = (Bean) contextual;
        if (customScopeContextHolder.getBeans().containsKey(bean.getBeanClass())) {
            return (T) customScopeContextHolder.getBeans().get(bean.getBeanClass());
        } else {
            return null;
        }
    }

    @Override
    public <T> T get(final Contextual<T> contextual, final CreationalContext<T> creationalContext) {
        Bean bean = (Bean) contextual;
        if (customScopeContextHolder.getBeans().containsKey(bean.getBeanClass())) {
            return (T) customScopeContextHolder.getBean(bean.getBeanClass());
        } else {
            T t = (T) bean.create(creationalContext);
            customScopeContextHolder.putBean(bean.getBeanClass(), t);
            return t;
        }
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return MyScope.class;
    }

    public boolean isActive() {
        return true;
    }

    public void passivate(@Observes KillEvent killEvent) {
        if (customScopeContextHolder.getBeans().containsKey(killEvent.getBeanType())) {
            customScopeContextHolder.getBeans().remove(killEvent.getBeanType());
        }
    }
}