package ai.backend.api;

import ai.backend.api.dtos.ProductsGetResponseDTO;
import ai.backend.api.dtos.ProductsPostRequestDTO;
import ai.backend.application.commands.CommandDispatcher;
import ai.backend.application.commands.uploadProductText.UploadProductTextCommand;
import ai.backend.application.queries.QueryDispatcher;
import ai.backend.application.queries.searchProducts.SearchProductsQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductsController {

    final CommandDispatcher commandDispatcher;
    final QueryDispatcher queryDispatcher;
    public ProductsController(CommandDispatcher commandDispatcher, QueryDispatcher queryDispatcher) {
        this.commandDispatcher = commandDispatcher;
        this.queryDispatcher = queryDispatcher;
    }

    @PostMapping
    public ResponseEntity<String> post(@RequestBody ProductsPostRequestDTO body) {
        commandDispatcher.dispatch(new UploadProductTextCommand(body.description()));
        return ResponseEntity.ok("success");
    }

    @GetMapping
    public ResponseEntity<ProductsGetResponseDTO> index(String search) {
        var result = queryDispatcher.disptach(new SearchProductsQuery(search));
        return ResponseEntity.ok(new ProductsGetResponseDTO(result));
    }
}

