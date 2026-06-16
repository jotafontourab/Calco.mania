# 🧉 Calco.Mania - Adesivos de Alma Sulista

Plataforma de e-commerce e catálogo digital para adesivos (stickers) focados na cultura do chimarrão, futebol e tradições do Sul do Brasil.

## 🚀 Arquitetura e Tecnologias

### Backend
- **Java 21** com **Spring Boot 3**
- **Spring Security** (Configurado para CORS com Vercel)
- **Spring Data JPA** (PostgreSQL)
- **Cloudinary API** (Sincronização automática de imagens na nuvem)
- **Lombok** para redução de boilerplate

### Frontend
- **Vue.js 3** (Composition API)
- **Vite** (Build tool rápida)
- **Vanilla CSS** (Design System Premium focado em estética "Mate & Campo")
- **Vue Router** para navegação fluida

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
- JDK 21 ou superior
- Node.js 18+
- Banco de Dados PostgreSQL

### 🖥️ Rodando o Backend (Local)
1. Navegue até a pasta `backend/`.
2. Configure as variáveis de ambiente no `application.yml` ou em um arquivo `.env` (DataSource, Cloudinary).
3. Execute o comando:
   ```bash
   ./mvnw spring-boot:run
   ```

### 🎨 Rodando o Frontend (Local)
1. Navegue até a pasta `frontend/`.
2. Instale as dependências:
   ```bash
   npm install
   ```
3. Crie um arquivo `.env` com a variável:
   ```env
   VITE_API_BASE_URL=http://localhost:8080
   ```
4. Inicie o servidor de desenvolvimento:
   ```bash
   npm run dev
   ```

---

## ☁️ Fluxo de Deploy e Sincronização

O projeto está otimizado para escala na nuvem:
- **Frontend**: Hospedado na **Vercel**.
- **Backend**: Hospedado no **Railway**.
- **Banco de Dados**: PostgreSQL Gerenciado (Railway).

### 🔄 Sincronização de Novos Stickers
Para adicionar novos adesivos de forma automática:
1. Adicione o arquivo `.png` na pasta `frontend/public/stickers/`.
2. Inicie o **backend localmente** uma vez.
3. O `DataLoader` irá registrar o novo sticker no banco.
4. O `CloudinaryMigrationRunner` fará o upload para a nuvem e atualizará a URL no banco de dados.
5. Faça o **Git Push** para atualizar os metadados no repositório.

---

## 📋 Variáveis de Ambiente Necessárias

### Backend (Railway/Local)
- `SPRING_DATASOURCE_URL`: URL de conexão JDBC.
- `CLOUDINARY_CLOUD_NAME`: Nome da sua conta Cloudinary.
- `CLOUDINARY_API_KEY`: Chave de API do Cloudinary.
- `CLOUDINARY_API_SECRET`: Segredo de API do Cloudinary.

### Frontend (Vercel)
- `VITE_API_BASE_URL`: URL pública do seu backend (ex: `https://meu-projeto.up.railway.app`).

---

## ✨ Funcionalidades Principais
- **Catálogo Temático**: Filtre stickers por "Tribos" (Tradição, Futebol, etc.).
- **QuickView**: Visualize detalhes do sticker sem sair da página principal.
- **Social Wall**: Mural "Nossa Tribo" com fotos enviadas pela comunidade.
- **Carrinho Dinâmico**: Lógica de descontos progressivos (ex: 10 por R$ 25).
- **Checkout via WhatsApp**: Envio de pedido formatado diretamente para o vendedor.

---
*Desenvolvido por João Balzano - 2026*
