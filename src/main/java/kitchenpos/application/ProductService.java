package kitchenpos.application;

import kitchenpos.application.dto.CreateProductRequest;
import kitchenpos.dao.ProductDao;
import kitchenpos.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Product create(final CreateProductRequest request) {
        Product product = request.toDomain();
        return productDao.save(product);
    }

    public List<Product> list() {
        return productDao.findAll();
    }
}
