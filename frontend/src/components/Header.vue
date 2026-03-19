<script setup>
import { useCart } from '../composables/useCart'

const { totalItems, generateWhatsappLink } = useCart()

const checkout = () => {
  if (totalItems.value > 0) {
    window.open(generateWhatsappLink(), '_blank')
  } else {
    alert('Sua cuia está vazia! Adicione alguns adesivos primeiro.')
  }
}
</script>

<template>
  <header class="header">
    <div class="container header-content">
      <div class="logo">
        <span class="logo-icon">🧉</span>
        <span class="logo-text">Calco.Mania</span>
      </div>
      <nav class="nav">
        <RouterLink to="/" class="nav-link">Coleções</RouterLink>
        <button class="btn-cuia" @click="checkout">
          <span class="cuia-icon">🧉</span> Minha Cuia ({{ totalItems }})
        </button>
      </nav>
    </div>
  </header>
</template>

<style scoped>
.header {
  background-color: var(--color-forest-dark);
  color: var(--color-cream);
  padding: 16px 0;
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: var(--shadow-sm);
}
.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-family: var(--font-serif);
  font-size: 1.5rem;
  font-weight: 700;
}
.nav {
  display: flex;
  align-items: center;
  gap: 24px;
}
.nav-link {
  font-weight: 500;
  transition: color var(--transition-fast);
}
.nav-link:hover {
  color: var(--color-leaf);
}
.btn-cuia {
  background-color: var(--color-leaf);
  color: var(--color-forest-dark);
  padding: 8px 16px;
  border-radius: var(--radius-full);
  font-weight: 600;
  transition: all var(--transition-fast);
  display: flex;
  align-items: center;
  gap: 6px;
}
.btn-cuia:hover {
  background-color: var(--color-cream);
  transform: translateY(-2px);
}
</style>
