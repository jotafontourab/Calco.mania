<script setup>
import { ref } from 'vue'
import { RouterView } from 'vue-router'
import Header from './components/Header.vue'
import AnnouncementBar from './components/AnnouncementBar.vue'
import CartModal from './components/CartModal.vue'
import QuickViewModal from './components/QuickViewModal.vue'

const isCartOpen = ref(false)

const toggleCart = () => {
  isCartOpen.value = !isCartOpen.value
}
</script>

<template>
  <AnnouncementBar />
  <Header @toggle-cart="toggleCart" />
  
  <RouterView />
  
  <CartModal :isOpen="isCartOpen" @close="isCartOpen = false" />
  <QuickViewModal />
</template>

<style>
/* Global styles */
:root {
  --transition-normal: 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  --transition-fast: 0.15s cubic-bezier(0.4, 0, 0.2, 1);
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
