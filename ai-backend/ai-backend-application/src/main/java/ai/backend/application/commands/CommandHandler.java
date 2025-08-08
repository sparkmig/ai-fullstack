package ai.backend.application.commands;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
