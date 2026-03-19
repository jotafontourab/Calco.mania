<script setup>
import { ref } from 'vue'
import Hero from '../components/Hero.vue'
import TribesNavigation from '../components/TribesNavigation.vue'
import ProductGrid from '../components/ProductGrid.vue'

const selectedThemeId = ref(null)

const handleFilter = (themeId) => {
  selectedThemeId.value = themeId
}
</script>

<template>
  <main>
    <Hero />
    <TribesNavigation @filter="handleFilter" :activeId="selectedThemeId" />
    <ProductGrid :themeId="selectedThemeId" />
  </main>
</template>

<style scoped>
</style>
