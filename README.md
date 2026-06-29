<h1>RickAndMortyQuest</h1>

<p>
  Aplicativo Android nativo desenvolvido em Java para teste técnico Android Júnior.
</p>

<p>
  O projeto implementa um fluxo completo com Splash animada, tela inicial, autenticação,
  menu principal, listagem paginada de personagens da API pública Rick and Morty,
  tela de detalhe com atualização de foto pela câmera nativa e módulo de CRUD de
  funcionários integrado a um backend local em Grails com MySQL.
</p>

<h2>Repositório</h2>

<p>
  <a href="https://github.com/BrunoFmachado/RickAndMortyQuest">
    https://github.com/BrunoFmachado/RickAndMortyQuest
  </a>
</p>

<h2>Tecnologias Utilizadas</h2>

<ul>
  <li>Java</li>
  <li>Android Views/XML</li>
  <li>MVVM</li>
  <li>ViewModel e LiveData</li>
  <li>RecyclerView</li>
  <li>DiffUtil/ListAdapter</li>
  <li>OkHttp3</li>
  <li>Gson</li>
  <li>Glide</li>
  <li>Material Components</li>
  <li>Activity Result API</li>
  <li>FileProvider</li>
  <li>SharedPreferences</li>
  <li>Grails/Groovy</li>
  <li>MySQL</li>
</ul>

<h2>Fluxo Geral do Aplicativo</h2>

<pre><code>Splash Activity
  -> Tela Inicial
  -> Login
  -> Menu Principal
  -> Personagens
  -> Detalhe do Personagem
  -> Câmera Nativa</code></pre>

<pre><code>Menu Principal
  -> Funcionários
  -> Cadastro / Edição / Exclusão</code></pre>

<h2>Credenciais de Teste</h2>

<pre><code>E-mail: admin@empresa.com
Senha: 123456</code></pre>

<h2>Resumo do App</h2>

<p>
  O RickAndMortyQuest foi construído para demonstrar domínio dos fundamentos de
  Android nativo, integração com APIs REST, organização em MVVM, tratamento de
  estados de tela e uso de recursos nativos do Android.
</p>

<p>O aplicativo possui dois módulos principais:</p>

<ul>
  <li>
    <strong>Personagens:</strong> consome a API pública Rick and Morty, exibe lista
    paginada, permite filtros e abre detalhes do personagem.
  </li>
  <li>
    <strong>Funcionários:</strong> consome backend próprio em Grails, com persistência
    em MySQL, permitindo listar, cadastrar, editar e excluir registros.
  </li>
</ul>

<h2>Funcionalidades Implementadas</h2>

<h3>Splash Activity</h3>

<p>
  A Splash Activity é exibida na abertura do aplicativo, com animação simples,
  identidade visual do projeto e transição automática para a próxima tela.
</p>

<h3>Tela Inicial</h3>

<p>
  A tela inicial apresenta o aplicativo e permite acessar o fluxo de autenticação.
</p>

<h3>Login</h3>

<p>
  A autenticação é realizada por meio do backend local.
</p>

<p><strong>Endpoint esperado:</strong></p>

<pre><code>POST /api/auth/login</code></pre>

<p><strong>Request esperado:</strong></p>

<pre><code>{
  "email": "admin@empresa.com",
  "senha": "123456"
}</code></pre>

<p><strong>Response esperado:</strong></p>

<pre><code>{
  "success": true,
  "token": "token-simples",
  "user": {
    "id": 1,
    "nome": "Administrador",
    "email": "admin@empresa.com"
  }
}</code></pre>

<p>
  Após login bem-sucedido, o app salva uma sessão local simples e navega para o
  menu principal.
</p>

<h3>Menu Principal</h3>

<p>O menu principal centraliza o acesso aos módulos do teste:</p>

<ul>
  <li>Personagens</li>
  <li>Funcionários</li>
  <li>Logout</li>
</ul>

<h3>Módulo de Personagens</h3>

<p>A listagem consome a API pública:</p>

<pre><code>https://rickandmortyapi.com/api/character</code></pre>

<p>Recursos implementados:</p>

<ul>
  <li>RecyclerView</li>
  <li>Paginação manual</li>
  <li>Carregamento de até 3 páginas</li>
  <li>Carregamento ao aproximar do fim da lista</li>
  <li>Prevenção de múltiplos carregamentos simultâneos</li>
  <li>Filtros por status, gênero e espécie</li>
  <li>Atualização automática ao selecionar filtros</li>
  <li>Estado de carregamento</li>
  <li>Estado de erro</li>
  <li>Estado de lista vazia</li>
  <li>Clique no card para abrir a tela de detalhe</li>
</ul>

<p>Cada card exibe:</p>

<ul>
  <li>Foto</li>
  <li>Nome</li>
  <li>Status</li>
  <li>Espécie</li>
  <li>Gênero</li>
  <li>Última localização</li>
</ul>

<h3>Filtros de Personagens</h3>

<p>Filtros disponíveis:</p>

<pre><code>Status:
- Todos
- Alive
- Dead
- unknown

Gênero:
- Todos
- Female
- Male
- Genderless
- unknown

Espécie:
- Todos
- Human
- Alien
- Humanoid
- Animal
- Robot
- Mythological Creature</code></pre>

<p>
  Ao selecionar uma opção, a listagem é atualizada automaticamente.
</p>

<h3>Tela de Detalhe do Personagem</h3>

<p>A tela de detalhe apresenta as principais informações do personagem:</p>

<ul>
  <li>ID</li>
  <li>Nome</li>
  <li>Status</li>
  <li>Espécie</li>
  <li>Tipo</li>
  <li>Gênero</li>
  <li>Origem</li>
  <li>Última localização</li>
  <li>Quantidade de episódios</li>
  <li>URL da API</li>
  <li>Data de criação</li>
  <li>Imagem atual</li>
</ul>

<h3>Atualização de Foto com Câmera Nativa</h3>

<p>
  Na tela de detalhe, o usuário pode atualizar a imagem do personagem usando a
  câmera nativa do aparelho.
</p>

<p>O fluxo utiliza:</p>

<ul>
  <li>Permissão de câmera</li>
  <li>Activity Result API</li>
  <li>FileProvider</li>
  <li>URI segura para a imagem</li>
  <li>Atualização visual imediata da foto</li>
  <li>Feedback visual de sucesso ou erro</li>
</ul>

<p>Após a captura, o app simula um envio POST para:</p>

<pre><code>https://jsonplaceholder.typicode.com/posts</code></pre>

<p>Exemplo de payload:</p>

<pre><code>{
  "characterId": 1,
  "characterName": "Rick Sanchez",
  "capturedImageUri": "content://...",
  "capturedAt": "2026-06-29T00:00:00",
  "source": "camera"
}</code></pre>

<p>
  Não é necessário enviar o arquivo binário real da imagem. O objetivo é demonstrar
  a montagem de um request coerente com a captura.
</p>

<h3>Módulo de Funcionários</h3>

<p>
  O módulo de funcionários consome um backend próprio em Grails com persistência
  em MySQL.
</p>

<p>Campos utilizados:</p>

<ul>
  <li>ID</li>
  <li>Nome</li>
  <li>E-mail</li>
  <li>Cargo</li>
  <li>Salário</li>
  <li>Ativo</li>
  <li>Data de criação</li>
</ul>

<p>Funcionalidades implementadas:</p>

<ul>
  <li>Listar funcionários</li>
  <li>Cadastrar funcionário</li>
  <li>Editar funcionário</li>
  <li>Excluir funcionário</li>
  <li>Atualizar a lista após criar, editar ou excluir</li>
  <li>Exibir loading</li>
  <li>Exibir erro</li>
  <li>Exibir lista vazia</li>
  <li>Exibir feedback visual com Snackbar</li>
</ul>

<h2>Endpoints do Backend</h2>

<p><strong>Base local:</strong></p>

<pre><code>http://localhost:8080/</code></pre>

<p><strong>Base para emulador Android:</strong></p>

<pre><code>http://10.0.2.2:8080/</code></pre>

<table>
  <thead>
    <tr>
      <th>Funcionalidade</th>
      <th>Método</th>
      <th>Endpoint</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Login</td>
      <td>POST</td>
      <td><code>/api/auth/login</code></td>
    </tr>
    <tr>
      <td>Listar funcionários</td>
      <td>GET</td>
      <td><code>/api/funcionarios</code></td>
    </tr>
    <tr>
      <td>Detalhar funcionário</td>
      <td>GET</td>
      <td><code>/api/funcionarios/{id}</code></td>
    </tr>
    <tr>
      <td>Criar funcionário</td>
      <td>POST</td>
      <td><code>/api/funcionarios</code></td>
    </tr>
    <tr>
      <td>Editar funcionário</td>
      <td>PUT</td>
      <td><code>/api/funcionarios/{id}</code></td>
    </tr>
    <tr>
      <td>Excluir funcionário</td>
      <td>DELETE</td>
      <td><code>/api/funcionarios/{id}</code></td>
    </tr>
  </tbody>
</table>

<h2>Como Executar o App Android</h2>

<h3>Pré-requisitos</h3>

<ul>
  <li>Android Studio instalado</li>
  <li>JDK compatível com o projeto</li>
  <li>Android SDK com compileSdk e targetSdk 34 ou superior</li>
  <li>Emulador Android ou dispositivo físico</li>
  <li>Backend Grails rodando localmente</li>
  <li>MySQL rodando localmente</li>
</ul>

<h3>Clonar o Projeto</h3>

<pre><code>git clone https://github.com/BrunoFmachado/RickAndMortyQuest.git
cd RickAndMortyQuest</code></pre>

<h3>Abrir no Android Studio</h3>

<ol>
  <li>Abra o Android Studio.</li>
  <li>Clique em <code>Open</code>.</li>
  <li>Selecione a pasta <code>RickAndMortyQuest</code>.</li>
  <li>Aguarde a sincronização do Gradle.</li>
  <li>Execute o app em um emulador ou dispositivo físico.</li>
</ol>

<h3>Rodar Build pelo Terminal</h3>

<p>No Windows:</p>

<pre><code>.\gradlew.bat clean assembleDebug</code></pre>

<p>No Linux/macOS:</p>

<pre><code>./gradlew clean assembleDebug</code></pre>

<h2>Como Executar o Backend Local</h2>

<h3>Pré-requisitos</h3>

<ul>
  <li>Grails instalado</li>
  <li>Groovy configurado</li>
  <li>MySQL instalado</li>
  <li>Banco local criado</li>
</ul>

<h3>Criar Banco MySQL</h3>

<pre><code>CREATE DATABASE rick_morty_quest CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;</code></pre>

<p>Configure o backend Grails com os dados do banco local.</p>

<pre><code>dataSource.url=jdbc:mysql://localhost:3306/rick_morty_quest
dataSource.username=root
dataSource.password=sua_senha</code></pre>

<h3>Subir o Backend</h3>

<p>Na pasta do backend:</p>

<pre><code>grails run-app</code></pre>

<p>O backend deverá ficar disponível em:</p>

<pre><code>http://localhost:8080/</code></pre>

<h2>Comunicação do App com o Backend Local</h2>

<h3>Emulador Android</h3>

<p>Para emulador Android, use:</p>

<pre><code>http://10.0.2.2:8080/</code></pre>

<h3>Celular Físico com USB</h3>

<p>Com o celular conectado por USB, use:</p>

<pre><code>adb reverse tcp:8080 tcp:8080</code></pre>

<h3>Celular Físico na Mesma Rede Wi-Fi</h3>

<p>Descubra o IP local da máquina.</p>

<p>No Windows:</p>

<pre><code>ipconfig</code></pre>

<p>Exemplo de base URL:</p>

<pre><code>http://192.168.0.10:8080/</code></pre>

<p>
  O celular e o computador precisam estar conectados à mesma rede.
</p>

<h2>Arquitetura</h2>

<p>O aplicativo segue MVVM.</p>

<pre><code>Activity
  -> ViewModel
  -> Repository
  -> RemoteDataSource
  -> OkHttp3</code></pre>

<table>
  <thead>
    <tr>
      <th>Camada</th>
      <th>Responsabilidade</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Activity</td>
      <td>Renderizar UI, observar estados e receber ações do usuário</td>
    </tr>
    <tr>
      <td>ViewModel</td>
      <td>Controlar estado da tela e regras de apresentação</td>
    </tr>
    <tr>
      <td>Repository</td>
      <td>Isolar a origem dos dados</td>
    </tr>
    <tr>
      <td>RemoteDataSource</td>
      <td>Executar requisições HTTP</td>
    </tr>
    <tr>
      <td>Model/DTO</td>
      <td>Representar dados da aplicação e respostas da API</td>
    </tr>
  </tbody>
</table>

<h2>Estrutura do Projeto</h2>

<pre><code>app/
  src/
    main/
      java/com/example/rickandmortyquest/
        core/
          base/
          network/
          session/
          state/
          ui/
        data/
          remote/
          repository/
        domain/
          model/
          repository/
        feature/
          auth/
          home/
          menu/
          characters/
          employees/
      res/
        drawable/
        layout/
        values/
        xml/</code></pre>

<h2>Estados de UI</h2>

<p>As telas tratam estados de:</p>

<ul>
  <li>Carregamento</li>
  <li>Sucesso</li>
  <li>Erro</li>
  <li>Lista vazia</li>
  <li>Estado inicial</li>
</ul>

<p>Estados utilizados:</p>

<pre><code>Idle
Loading
Success
Empty
Error</code></pre>

<h2>Permissões</h2>

<p>O app utiliza permissão de câmera:</p>

<pre><code>&lt;uses-permission android:name="android.permission.CAMERA" /&gt;</code></pre>

<p>
  Para salvar e abrir a imagem capturada com segurança, o app utiliza
  <code>FileProvider</code>.
</p>

<h2>Teste Manual Sugerido</h2>

<h3>Login</h3>

<ol>
  <li>Abrir o app.</li>
  <li>Aguardar Splash.</li>
  <li>Entrar na tela de login.</li>
  <li>Usar <code>admin@empresa.com</code> e <code>123456</code>.</li>
  <li>Confirmar abertura do menu.</li>
</ol>

<h3>Personagens</h3>

<ol>
  <li>Abrir módulo de personagens.</li>
  <li>Confirmar carregamento da lista.</li>
  <li>Testar filtro por status.</li>
  <li>Testar filtro por gênero.</li>
  <li>Testar filtro por espécie.</li>
  <li>Rolar a lista para carregar novas páginas.</li>
  <li>Abrir um personagem.</li>
  <li>Conferir informações detalhadas.</li>
  <li>Atualizar foto pela câmera.</li>
  <li>Confirmar troca da imagem na tela.</li>
</ol>

<h3>Funcionários</h3>

<ol>
  <li>Abrir módulo de funcionários.</li>
  <li>Confirmar listagem.</li>
  <li>Cadastrar funcionário.</li>
  <li>Editar funcionário.</li>
  <li>Excluir funcionário.</li>
  <li>Confirmar atualização da lista.</li>
</ol>

<h2>Checklist do Teste Técnico</h2>

<table>
  <thead>
    <tr>
      <th>Requisito</th>
      <th>Status</th>
    </tr>
  </thead>
  <tbody>
    <tr><td>Splash Activity animada</td><td>Implementado</td></tr>
    <tr><td>Tela inicial</td><td>Implementado</td></tr>
    <tr><td>Login via backend próprio</td><td>Implementado</td></tr>
    <tr><td>Sessão local simples</td><td>Implementado</td></tr>
    <tr><td>Menu principal</td><td>Implementado</td></tr>
    <tr><td>Listagem de personagens</td><td>Implementado</td></tr>
    <tr><td>Paginação manual até 3 páginas</td><td>Implementado</td></tr>
    <tr><td>Filtros por status, gênero e espécie</td><td>Implementado</td></tr>
    <tr><td>Card com foto e dados principais</td><td>Implementado</td></tr>
    <tr><td>Tela de detalhe do personagem</td><td>Implementado</td></tr>
    <tr><td>Câmera nativa</td><td>Implementado</td></tr>
    <tr><td>FileProvider</td><td>Implementado</td></tr>
    <tr><td>POST simulado da captura</td><td>Implementado</td></tr>
    <tr><td>Lista de funcionários</td><td>Implementado</td></tr>
    <tr><td>Cadastro de funcionário</td><td>Implementado</td></tr>
    <tr><td>Edição de funcionário</td><td>Implementado</td></tr>
    <tr><td>Exclusão de funcionário</td><td>Implementado</td></tr>
    <tr><td>Backend Grails</td><td>Implementado</td></tr>
    <tr><td>MySQL local</td><td>Implementado</td></tr>
    <tr><td>Tema escuro</td><td>Implementado</td></tr>
    <tr><td>Estados de loading, erro e vazio</td><td>Implementado</td></tr>
  </tbody>
</table>

<h2>Decisões Técnicas</h2>

<ul>
  <li>Java foi usado como linguagem principal.</li>
  <li>XML foi usado para construção das telas.</li>
  <li>MVVM foi usado para separar UI, estado e dados.</li>
  <li>OkHttp3 foi usado para requisições HTTP.</li>
  <li>Glide foi usado para carregamento das imagens.</li>
  <li>FileProvider foi usado para compatibilidade com Android moderno no fluxo de câmera.</li>
  <li>SharedPreferences foi usado para sessão local simples.</li>
  <li>O backend local atende autenticação e CRUD de funcionários.</li>
  <li>A API pública Rick and Morty atende a listagem, filtros e paginação de personagens.</li>
</ul>

<h2>Comandos Git</h2>

<pre><code>git init
git branch -M main
git remote add origin https://github.com/BrunoFmachado/RickAndMortyQuest.git
git add .
git commit -m "Finaliza teste tecnico Android Rick and Morty"
git push -u origin main</code></pre>

<h2>Autor</h2>

<p>
  Desenvolvido por Bruno Ferreira Machado.
</p>
