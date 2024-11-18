package ma.emsi.billingservice.web;

import ma.emsi.billingservice.entities.Bill;
import ma.emsi.billingservice.repository.BillRepository;
import ma.emsi.billingservice.repository.ProductItemRepository;
import ma.emsi.billingservice.services.CustomerRestClient;
import ma.emsi.billingservice.services.ProductRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BillRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    public BillRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }
    @GetMapping("/fullBill/{id}")
    public Bill bill(@PathVariable Long id){
       Bill bill=  billRepository.findById(id).get();
       bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));
       bill.getProductItems().forEach(pi ->{
           pi.setProduct(productRestClient.findProductById(pi.getProductId()));
       });
        return bill;
    }
}
