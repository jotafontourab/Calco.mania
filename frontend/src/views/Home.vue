<script setup>
import { ref } from 'vue'
import Hero from '../components/Hero.vue'
import TribesNavigation from '../components/TribesNavigation.vue'
import ProductGrid from '../components/ProductGrid.vue'
import SocialWall from '../components/SocialWall.vue'

const selectedThemeId = ref(null)

const handleFilter = (themeId) => {
  selectedThemeId.value = themeId
  // Smooth scroll to stickers on mobile/selection
  setTimeout(() => {
    const grid = document.getElementById('stickers-grid')
    if (grid) {
      grid.scrollIntoView({ behavior: 'smooth', block: 'start' })
    }
  }, 100)
}
</script>

<template>
  <main>
    <Hero />
    <TribesNavigation @filter="handleFilter" :activeId="selectedThemeId" />
    <ProductGrid :themeId="selectedThemeId" id="stickers-grid" />
    <SocialWall />
    
    <footer class="site-footer">
      <div class="container footer-content">
        <div class="footer-logo">🧉 Calco.Mania</div>
        <p>&copy; 2026 - Todos os direitos reservados. Feito com paixão pelo Chimarrão.</p>
      </div>
    </footer>
  </main>
</template>

<style scoped>
.site-footer {
  background-color: var(--color-forest-dark);
  color: var(--color-cream);
  padding: 60px 0;
  text-align: center;
}
.footer-logo {
  font-family: var(--font-serif);
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 16px;
}
.footer-note {
  opacity: 0.6;
}
</style>
