package ai.backend.application.commands.uploadProductText;

import ai.backend.application.commands.CommandValidationException;
import ai.backend.application.commands.CommandValidator;
import org.springframework.stereotype.Service;

@Service
public class UploadProductTextCommandValidator implements CommandValidator<UploadProductTextCommand> {
    @Override
    public void validate(UploadProductTextCommand command) {
        var description = command.description();
        if (description == null || description.isEmpty()) {
            throw new CommandValidationException("description is required");
        }
    }
}
