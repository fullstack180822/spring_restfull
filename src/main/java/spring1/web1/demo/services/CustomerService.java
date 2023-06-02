package spring1.web1.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring1.web1.demo.model.ClientFaultException;
import spring1.web1.demo.model.Customer;
import spring1.web1.demo.model.CustomerStatus;
import spring1.web1.demo.model.ExceedVIPException;
import spring1.web1.demo.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${maxvip}")
    private Integer maxVIP;

    @Override
    public Customer createCustomer(Customer customer) throws ClientFaultException {
//        System.out.println(maxVIP);
        if (customer.getStatus() == CustomerStatus.VIP &&
                customerRepository.getCustomerByStatus(CustomerStatus.VIP).size() >= maxVIP)
        {
            throw new ExceedVIPException(String.format("Cannot create more VIP customers, for customer: %s %s.", customer.getLastName(), customer.getFirstName()));
        }
        // without id
        //return customerRepository.createCustomer(customer);

        // with id
        return customerRepository.createCustomerReturnId(customer);
    }

    @Override
    public void updateCustomer(Customer customer, Integer id) {
        customerRepository.updateCustomer(customer, id);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteCustomer(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public List<Integer> getAllIds() {
        return customerRepository.getAllIds();
    }


}
