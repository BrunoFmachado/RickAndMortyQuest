package rick.employees.api

import grails.gorm.services.Service

@Service(Employee)
interface EmployeeService {

    Employee get(Serializable id)

    List<Employee> list(Map args)

    Long count()

    Employee delete(Serializable id)

    Employee save(Employee employee)

}
