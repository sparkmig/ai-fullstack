package ai.backend.application.commands.uploadProductText;

import ai.backend.application.commands.Command;

public record UploadProductTextCommand(String description) implements Command {
}
