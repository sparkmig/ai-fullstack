package ai.backend.application.queries;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
public class QueryDispatcher {

    private final Map<Class<? extends Query>, QueryHandler> handlers;
    private final Map<Class<? extends Query>, QueryValidator> validators;

    public QueryDispatcher(ApplicationContext applicationContext) {
        handlers = new HashMap<>();
        validators = new HashMap<>();

        Map<String, QueryHandler> handlersMap = applicationContext.getBeansOfType(QueryHandler.class);
        Map<String, QueryValidator> validatorsMap = applicationContext.getBeansOfType(QueryValidator.class);

        for (QueryValidator validator : validatorsMap.values()) {
            Type genericInterface = validator.getClass().getGenericInterfaces()[0];
            Type commandType = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
            validators.put((Class<? extends Query>) commandType, validator);
        }

        for (QueryHandler handler : handlersMap.values()) {
            Type genericInterface = handler.getClass().getGenericInterfaces()[0];
            Type commandType = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
            handlers.put((Class<? extends Query>) commandType, handler);
        }
    }

    public <R> R disptach(Query<R> query) {
        var  handler = handlers.get(query.getClass());
        if (handler == null) {
            throw new RuntimeException("No handler for " + query.getClass());
        }
        var validator = validators.get(query.getClass());
        if (validator != null) {
            validator.validate(query);
        }
        return (R) handler.handle(query);
    }
}