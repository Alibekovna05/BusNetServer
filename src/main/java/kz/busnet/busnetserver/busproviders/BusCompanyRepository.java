package kz.busnet.busnetserver.busproviders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BusCompanyRepository  extends JpaRepository<BusCompany, Long>, JpaSpecificationExecutor<BusCompany> {
//
//    Page<Book> findBusCompanies(Pageable pageable);
}
