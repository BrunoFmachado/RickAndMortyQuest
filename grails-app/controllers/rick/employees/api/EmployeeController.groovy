package rick.employees.api

import grails.converters.JSON
import grails.gorm.transactions.Transactional

class EmployeeController {

    static responseFormats = ['json']

    def index() {
        render Employee.list(sort: 'id', order: 'asc') as JSON
    }

    def show(Long id) {
        Employee employee = Employee.get(id)

        if (!employee) {
            render status: 404, contentType: 'application/json', text: '{"message":"Funcionário não encontrado"}'
            return
        }

        render employee as JSON
    }

    @Transactional
    def save() {
        def body = request.JSON

        Employee employee = new Employee(
                nome: body.nome,
                email: body.email,
                cargo: body.cargo,
                salario: body.salario,
                ativo: body.ativo == null ? true : body.ativo
        )

        if (!employee.validate()) {
            render status: 422, contentType: 'application/json', text: (employee.errors as JSON).toString()
            return
        }

        employee.save(flush: true)

        render status: 201, contentType: 'application/json', text: (employee as JSON).toString()
    }

    @Transactional
    def update(Long id) {
        Employee employee = Employee.get(id)

        if (!employee) {
            render status: 404, contentType: 'application/json', text: '{"message":"Funcionário não encontrado"}'
            return
        }

        def body = request.JSON

        employee.nome = body.nome
        employee.email = body.email
        employee.cargo = body.cargo
        employee.salario = body.salario
        employee.ativo = body.ativo == null ? true : body.ativo

        if (!employee.validate()) {
            render status: 422, contentType: 'application/json', text: (employee.errors as JSON).toString()
            return
        }

        employee.save(flush: true)

        render status: 200, contentType: 'application/json', text: (employee as JSON).toString()
    }

    @Transactional
    def delete(Long id) {
        Employee employee = Employee.get(id)

        if (!employee) {
            render status: 404, contentType: 'application/json', text: '{"message":"Funcionário não encontrado"}'
            return
        }

        employee.delete(flush: true)

        render status: 204
    }
}