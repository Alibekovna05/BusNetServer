//package kz.busnet.busnetserver.schedule;
//
//
//import jakarta.persistence.*;
//import kz.busnet.busnetserver.bus.Bus;
//import kz.busnet.busnetserver.common.BaseEntity;
//import kz.busnet.busnetserver.route.Route;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.SuperBuilder;
//
//@Getter
//@Setter
//@SuperBuilder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class BusSchedule extends BaseEntity {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.AUTO)
//        private Integer id;
//
//        @ManyToOne
//        private Bus bus;
//
//        @ManyToOne
//        private Route route;
//        private String departureTime;
//        private String arrivalTime;
//
//        // getters and setters
//    }