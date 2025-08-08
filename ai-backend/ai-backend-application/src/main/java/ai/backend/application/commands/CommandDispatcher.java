package ai.backend.application.commands;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandDispatcher {

    private final Map<Class<? extends Command>, CommandHandler> handlers;
    private final Map<Class<? extends Command>, CommandValidator> validators;

    public CommandDispatcher(ApplicationContext applicationContext) {
        handlers = new HashMap<>();
        validators = new HashMap<>();

        Map<String, CommandHandler> handlersMap = applicationContext.getBeansOfType(CommandHandler.class);
        Map<String, CommandValidator> validatorsMap = applicationContext.getBeansOfType(CommandValidator.class);

        for (CommandValidator validator : validatorsMap.values()) {
            Type genericInterface = validator.getClass().getGenericInterfaces()[0];
            Type commandType = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
            validators.put((Class<? extends Command>) commandType, validator);
        }

        for (CommandHandler handler : handlersMap.values()) {
            Type genericInterface = handler.getClass().getGenericInterfaces()[0];
            Type commandType = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
            handlers.put((Class<? extends Command>) commandType, handler);
        }
    }

    public void dispatch(Command command) {
        CommandValidator validator = validators.get(command.getClass());
        CommandHandler handler = handlers.get(command.getClass());
        if (handler != null) {
            if (validator != null) {
                validator.validate(command);
            }
            handler.handle(command);
        } else {
            throw new IllegalArgumentException("No handler found for command: " + command.getClass().getName());
        }
    }
}