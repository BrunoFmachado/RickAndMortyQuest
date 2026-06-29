package rick.employees.api

class AuthController {

    static responseFormats = ['json']
    static allowedMethods = [login: "POST"]

    def login() {
        def body = request.JSON

        if (body.email == 'admin@empresa.com' && body.senha == '123456') {
            render(contentType: 'application/json') {
                success true
                token 'token-simples'
                user {
                    id 1
                    nome 'Administrador'
                    email 'admin@empresa.com'
                }
            }
            return
        }

        render status: 401, contentType: 'application/json', text: '{"success":false,"message":"Credenciais inválidas"}'
    }
}