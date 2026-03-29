<script setup>
import { useCart } from '../composables/useCart'

const { totalItems } = useCart()
const emit = defineEmits(['toggle-cart'])

const scrollToTribes = () => {
  const element = document.getElementById('tribes-section')
  if (element) {
    element.scrollIntoView({ behavior: 'smooth' })
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
        <a href="#tribes-section" class="nav-link" @click.prevent="scrollToTribes">Coleções</a>
        <button class="btn-cuia" @click="emit('toggle-cart')">
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
/* Desktop layout handled by basic styles */
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

/* Mobile Media Query */
@media (max-width: 640px) {
  .logo-text {
    font-size: 1.125rem;
  }
  .logo-icon {
    font-size: 1.25rem;
  }
  .nav {
    gap: 12px;
  }
  .nav-link {
    font-size: 0.875rem;
  }
  .btn-cuia {
    padding: 6px 12px;
    font-size: 0.8125rem;
    gap: 4px;
    white-space: nowrap;
  }
  .btn-cuia .cuia-icon {
    display: none; /* Hide icon on very small screens to save space */
  }
}
@media (max-width: 380px) {
  .nav-link {
    display: none; /* Hide Coleções on tiny screens */
  }
}
</style>
