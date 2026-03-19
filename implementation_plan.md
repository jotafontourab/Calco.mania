# Calco.Mania - Plano de Construção da Aplicação Web

Este documento detalha o plano de desenvolvimento para a plataforma **Calco.Mania**, focada em stickers de cultura do chimarrão e futebol sul-americano.

## 1. Identidade Visual e Conceito
- **Paleta de Cores**: Verde floresta profundo (#1B4332), Creme suave (#F8F9FA), e tons terrosos.
- **Tipografia**: Combinação de fontes manuscritas (como no logo) com sans-serif modernas e limpas.
- **Conceito "Tribos"**: Categorização não por "tipo de adesivo", mas por "pertencimento" (Ex: Tribo do Inter, Tribo do Grêmio, Tribo dos Mateiros).
- **Ritual Digital**: Interface que evoque a calma do chimarrão e a paixão do futebol.

## 2. Tecnologias Utilizadas
- **Backend**: Java 21 + Spring Boot 3 (Painel Administrativo)
- **Frontend**: Vue.js 3 + Vanilla CSS (Foco em Design System Premium)
- **Banco de Dados**: PostgreSQL (Catálogo de Produtos e Fotos da Galeria)
- **Integração**: Redirecionamento dinâmico para WhatsApp com mensagem pré-formatada.

## 3. Arquitetura da Aplicação
- **Backend (Spring Boot)**:
    - `Security`: Acesso restrito ao Painel Administrativo.
    - `Controller`: Gestão de produtos, fotos de clientes e configuração de WhatsApp.
- **Frontend (Vue.js)**:
    - `Customizer`: Visualizador 2D de sticker sobre térmica.
    - `Admin`: Interface de gestão de catálogo.
    - `Gallery`: Mural social "Nossa Tribo".

## 4. Cronograma de Implementação

### Fase 1: Design System & Frontend (ATUAL)
- Setup de projeto com Vite + Vue 3.
- Criação do `index.css` com paleta "Mate & Campo".
- Implementação da Landing Page de Catálogo profissional.

### Fase 2: Funcionalidade de Preview
- Componente simples de visualização (Sticker sobre Imagem de Térmica).
- Mockups 2D para simulação.

### Fase 3: Backend & Painel Administrativo
- Configuração de tabelas e API.
- Tela de Login e Dashboard de gerenciamento de produtos.

### Fase 4: Integração WhatsApp & Galeria
- Lógica de geração de link de WhatsApp (Ex: `wa.me/...?text=Gostaria do sticker [X]`).
- Upload e exibição de fotos da comunidade.

## 5. Próximos Passos
1. Validar as categorias principais ("Tribos").
2. Definir o fluxo de venda (Direto no site ou link externo).
3. Iniciar o desenvolvimento do Backend.
