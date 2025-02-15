package kitchenpos.ui;

import java.net.URI;
import java.util.List;
import kitchenpos.application.CreateMenuCommand;
import kitchenpos.application.MenuDto;
import kitchenpos.application.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuRestController {
    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/api/menus")
    public ResponseEntity<MenuDto> create(@RequestBody final CreateMenuCommand request) {
        final MenuDto created = menuService.create(request);
        final URI uri = URI.create("/api/menus/" + created.getId());
        return ResponseEntity.created(uri)
                .body(created)
                ;
    }

    @GetMapping("/api/menus")
    public ResponseEntity<List<MenuDto>> list() {
        return ResponseEntity.ok()
                .body(menuService.list())
                ;
    }
}
