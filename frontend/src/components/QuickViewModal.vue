<script setup>
import { computed } from 'vue'
import { useQuickView } from '../composables/useQuickView'
import { useCart } from '../composables/useCart'

const { state, closeQuickView } = useQuickView()
const { addToCart } = useCart()

const item = computed(() => state.item)
const isOpen = computed(() => state.isOpen)

const handleAdd = () => {
  if (item.value) {
    addToCart(item.value)
    closeQuickView()
  }
}
</script>

<template>
  <Transition name="fade">
    <div v-if="isOpen && item" class="qv-overlay" @click.self="closeQuickView">
      <div class="qv-container">
        <button class="qv-close" @click="closeQuickView">&times;</button>
        
        <div class="qv-content">
          <div class="qv-image-side">
            <img :src="item.imageUrl" :alt="item.name" />
          </div>
          
          <div class="qv-details-side">
            <span class="qv-theme">{{ item.theme?.name || 'Geral' }}</span>
            <h2 class="qv-title">{{ item.name }}</h2>
            <p class="qv-price">R$ {{ item.price.toFixed(2) }}</p>
            
            <div class="qv-description">
              <p>Este adesivo premium é produzido com vinil de alta qualidade, garantindo resistência à água, ao sol e ao tempo. Ideal para personalizar sua térmica, cuia, notebook ou carro.</p>
              <ul>
                <li>✓ À prova d'água</li>
                <li>✓ Proteção UV</li>
                <li>✓ Cola de alta aderência (não deixa resíduos)</li>
              </ul>
            </div>
            
            <button class="btn-primary qv-add-btn" @click="handleAdd">
              Adicionar à Cuia
            </button>
          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<style scoped>
.qv-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(8, 28, 21, 0.85); /* Slightly darker than cart */
  backdrop-filter: blur(8px);
  z-index: 2000;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.qv-container {
  background: var(--color-white);
  width: 100%;
  max-width: 900px;
  max-height: 90vh; /* Prevents modal from being larger than screen */
  border-radius: var(--radius-lg);
  position: relative;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  animation: slideUp 0.4s cubic-bezier(0.16, 1, 0.3, 1);
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(40px) scale(0.95); }
  to { opacity: 1; transform: translateY(0) scale(1); }
}

.qv-close {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 2.5rem;
  line-height: 1;
  color: var(--color-forest-base);
  z-index: 100; /* Ensure it stays above everything */
  transition: transform 0.2s;
  background: white; /* Add background to close button for visibility */
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: var(--shadow-sm);
}

.qv-close:hover {
  transform: rotate(90deg) scale(1.1);
}

.qv-content {
  display: flex;
  flex-direction: row;
  height: 100%;
  overflow-y: auto; /* Allow internal scrolling if content is long */
}

@media (max-width: 768px) {
  .qv-content {
    flex-direction: column;
  }
  .qv-image-side {
    height: 300px;
    flex: none;
    padding: 30px;
  }
  .qv-details-side {
    padding: 30px 20px;
  }
  .qv-title {
    font-size: 1.75rem;
  }
}

.qv-image-side {
  flex: 1.2;
  background: var(--color-cream-dark);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
}

.qv-image-side img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  filter: drop-shadow(0 10px 20px rgba(0,0,0,0.15));
}

.qv-details-side {
  flex: 1;
  padding: 60px 40px;
  display: flex;
  flex-direction: column;
  background: white;
}

.qv-theme {
  display: inline-block;
  font-size: 0.875rem;
  color: var(--color-leaf);
  text-transform: uppercase;
  letter-spacing: 0.1em;
  font-weight: 700;
  margin-bottom: 12px;
}

.qv-title {
  font-family: var(--font-sans);
  font-size: 2.5rem;
  color: var(--color-forest-dark);
  line-height: 1.1;
  margin-bottom: 16px;
}

.qv-price {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-forest-base);
  margin-bottom: 24px;
}

.qv-description {
  color: var(--color-forest-soft);
  line-height: 1.6;
  margin-bottom: 32px;
  font-size: 0.95rem;
}

.qv-description ul {
  list-style: none;
  padding: 0;
  margin-top: 15px;
}

.qv-description li {
  margin-bottom: 8px;
  color: var(--color-leaf);
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
}

.qv-add-btn {
  margin-top: auto;
  padding: 14px 28px;
  font-size: 1rem;
  width: 100%;
}

/* Base transitions from App.vue handle overlay fade */
</style>
