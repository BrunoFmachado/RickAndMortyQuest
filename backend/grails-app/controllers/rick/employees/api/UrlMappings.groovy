package rick.employees.api

class UrlMappings {

    static mappings = {
        "/api/auth/login"(controller: "auth", action: "login", method: "POST")

        "/api/funcionarios"(controller: "employee") {
            action = [GET: "index", POST: "save"]
        }

        "/api/funcionarios/$id"(controller: "employee") {
            action = [GET: "show", PUT: "update", DELETE: "delete"]
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}