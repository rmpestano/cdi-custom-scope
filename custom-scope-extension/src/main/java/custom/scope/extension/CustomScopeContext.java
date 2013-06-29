package custom.scope.extension;

import custom.scope.extension.CustomScopeContextHolder.CustomScopeInstance;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;
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
    
    private Logger log = Logger.getLogger(getClass().getSimpleName());

    private CustomScopeContextHolder customScopeContextHolder;
    
    public CustomScopeContext() {
        log.info("Init");
        this.customScopeContextHolder = CustomScopeContextHolder.getInstance();
    }

    @Override
    public <T> T get(final Contextual<T> contextual) {
        Bean bean = (Bean) contextual;
        if (customScopeContextHolder.getBeans().containsKey(bean.getBeanClass())) {
            return (T) customScopeContextHolder.getBean(bean.getBeanClass()).instance;
        } else {
            return null;
        }
    }

    @Override
    public <T> T get(final Contextual<T> contextual, final CreationalContext<T> creationalContext) {
        Bean bean = (Bean) contextual;
        if (customScopeContextHolder.getBeans().containsKey(bean.getBeanClass())) {
            return (T) customScopeContextHolder.getBean(bean.getBeanClass()).instance;
        } else {
            T t = (T) bean.create(creationalContext);
            CustomScopeInstance customInstance = new CustomScopeInstance();
            customInstance.bean = bean;
            customInstance.ctx = creationalContext;
            customInstance.instance = t;
            customScopeContextHolder.putBean(customInstance);
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
            customScopeContextHolder.destroyBean(customScopeContextHolder.getBean(killEvent.getBeanType()));
        }
    }
}