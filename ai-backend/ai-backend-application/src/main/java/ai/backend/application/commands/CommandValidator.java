package ai.backend.application.commands;

public interface CommandValidator<T extends Command> {
    void validate(T command);
}
