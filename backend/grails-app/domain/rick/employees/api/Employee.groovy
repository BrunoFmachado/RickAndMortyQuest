package rick.employees.api

class Employee {

    String nome
    String email
    String cargo
    BigDecimal salario
    Boolean ativo = true

    Date dateCreated
    Date lastUpdated

    static constraints = {
        nome blank: false
        email blank: false, email: true, unique: true
        cargo blank: false
        salario nullable: false, min: 0.0G
        ativo nullable: false
    }
}