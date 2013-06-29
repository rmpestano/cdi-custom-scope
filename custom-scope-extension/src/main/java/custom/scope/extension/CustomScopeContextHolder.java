/*
 * Copyright 2013 rmpestano.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package custom.scope.extension;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

/**
 *
 * @author rmpestano
 */
public class CustomScopeContextHolder implements Serializable {

    private static CustomScopeContextHolder INSTANCE;
    private Map<Class, CustomScopeInstance> beans;//we will have only one instance of a type so the key is a class

    private CustomScopeContextHolder() {
        beans = Collections.synchronizedMap(new HashMap<Class, CustomScopeInstance>());
    }

    public synchronized static CustomScopeContextHolder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomScopeContextHolder();
        }
        return INSTANCE;
    }

    public Map<Class, CustomScopeInstance> getBeans() {
        return beans;
    }

    public CustomScopeInstance getBean(Class type) {
        return getBeans().get(type);
    }

    public void putBean(CustomScopeInstance customInstance) {
        getBeans().put(customInstance.bean.getBeanClass(), customInstance);
    }

    void destroyBean(CustomScopeInstance customScopeInstance) {
        getBeans().remove(customScopeInstance.bean.getBeanClass());
        customScopeInstance.bean.destroy(customScopeInstance.instance, customScopeInstance.ctx);
    }

    /**
     * wrap necessary properties so we can destroy the bean later:
     *
     * @see
     * CustomScopeContextHolder#destroyBean(custom.scope.extension.CustomScopeContextHolder.CustomScopeInstance)
     */
    public static class CustomScopeInstance<T> {

        Bean<T> bean;
        CreationalContext<T> ctx;
        T instance;
    }
}
