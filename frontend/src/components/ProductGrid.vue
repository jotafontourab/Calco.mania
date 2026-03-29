<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useCart } from '../composables/useCart'
import { useQuickView } from '../composables/useQuickView'

const props = defineProps(['themeId'])
const { addToCart } = useCart()
const { openQuickView } = useQuickView()

const products = ref([])
const loading = ref(true)
const displayLimit = ref(0) // 0 means all

const isMobile = () => window.innerWidth < 768

const fetchProducts = async () => {
  loading.value = true
  try {
    let url = 'http://localhost:8080/api/stickers'
    if (props.themeId) {
      url += `?themeId=${props.themeId}`
    }
    const response = await fetch(url)
    const data = await response.json()
    products.value = data
  } catch (error) {
    console.error('Error fetching products:', error)
  } finally {
    loading.value = false
  }
}

const displayedProducts = computed(() => {
  if (displayLimit.value === 0) return products.value
  return products.value.slice(0, displayLimit.value)
})

const handleAdd = (product) => {
  addToCart(product)
}

onMounted(() => {
  fetchProducts()
  // Set default limit based on device
  if (isMobile()) {
    displayLimit.value = 5
  } else {
    displayLimit.value = 0
  }
})

// Re-fetch when theme changes
watch(() => props.themeId, fetchProducts)
</script>

<template>
  <section class="products-section">
    <div class="container">
      <div class="section-header">
        <h2>{{ themeId ? 'Filtrando por Temas' : 'Últimos Lançamentos' }}</h2>
        <div class="header-controls">
          <p>Adesivos premium, resistentes à água, ao tempo e à sua térmica.</p>
          
          <div class="limit-selector" v-if="products.length > 0">
            <label for="limit">Mostrar:</label>
            <select id="limit" v-model="displayLimit">
              <option :value="5">5 stickers</option>
              <option :value="10">10 stickers</option>
              <option :value="20">20 stickers</option>
              <option :value="0">Mostrar Todos ({{ products.length }})</option>
            </select>
          </div>
        </div>
      </div>

      <div v-if="loading" class="loading">Buscando stickers...</div>
      
      <div v-else-if="products.length === 0" class="no-products">
        Nenhum sticker encontrado nesta categoria.
      </div>

      <div v-else class="products-grid">
        <div v-for="product in displayedProducts" :key="product.id" class="product-card">
          <div class="product-image" @click="openQuickView(product)">
            <img v-if="product.imageUrl" :src="product.imageUrl" :alt="product.name" />
            <div v-else class="product-image-placeholder">
              <span>Sticker Preview</span>
            </div>
            <div class="product-image-overlay">
              <span>Espiar Sticker</span>
            </div>
          </div>
          <div class="product-info">
            <span class="product-theme">{{ product.theme?.name || 'Geral' }}</span>
            <h3>{{ product.name }}</h3>
            <div class="product-footer">
              <span class="product-price">R$ {{ product.price.toFixed(2) }}</span>
              <button class="btn-primary btn-sm" @click="handleAdd(product)">
                <span>+</span> Cuia
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.products-section {
  padding: 80px 0;
}
.section-header {
  text-align: center;
  margin-bottom: 48px;
}
.header-controls {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  margin-top: 12px;
}
.limit-selector {
  display: flex;
  align-items: center;
  gap: 8px;
  background: var(--color-white);
  padding: 6px 12px;
  border-radius: var(--radius-sm);
  box-shadow: var(--shadow-sm);
  font-size: 0.875rem;
}
.limit-selector select {
  border: 1px solid var(--color-cream-dark);
  padding: 4px 8px;
  border-radius: 4px;
  background: white;
  color: var(--color-forest-dark);
  font-weight: 500;
  cursor: pointer;
}
.section-header p {
  color: var(--color-forest-base);
  font-size: 1.125rem;
}
@media (max-width: 640px) {
  .section-header h2 {
    font-size: 1.75rem;
  }
  .section-header p {
    font-size: 1rem;
  }
}
.loading, .no-products {
  text-align: center;
  padding: 40px;
  color: var(--color-forest-light);
  font-style: italic;
}
.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 32px;
}
.product-card {
  background-color: var(--color-white);
  border-radius: var(--radius-md);
  overflow: hidden;
  box-shadow: var(--shadow-sm);
  transition: all var(--transition-normal);
}
.product-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-md);
}
.product-image {
  height: 240px;
  background-color: var(--color-cream-dark);
  border-bottom: 1px solid var(--color-cream);
  overflow: hidden;
  position: relative;
  cursor: pointer;
}
.product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-normal);
}
.product-card:hover .product-image img {
  transform: scale(1.1);
}
.product-image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(8, 28, 21, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity var(--transition-normal);
}
.product-image-overlay span {
  color: var(--color-white);
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  font-size: 0.875rem;
  border: 2px solid var(--color-white);
  padding: 8px 16px;
  border-radius: var(--radius-sm);
  transform: translateY(10px);
  transition: transform var(--transition-normal);
}
.product-image:hover .product-image-overlay {
  opacity: 1;
}
.product-image:hover .product-image-overlay span {
  transform: translateY(0);
}
.product-image-placeholder {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--color-leather-base);
  font-family: var(--font-serif);
  font-style: italic;
}
.product-info {
  padding: 24px;
}
.product-theme {
  font-size: 0.875rem;
  color: var(--color-leaf);
  text-transform: uppercase;
  letter-spacing: 0.05em;
  font-weight: 600;
  margin-bottom: 8px;
  display: block;
}
.product-info h3 {
  font-family: var(--font-sans);
  font-size: 1.25rem;
  color: var(--color-forest-dark);
  margin-bottom: 16px;
}
.product-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.product-price {
  font-weight: 700;
  font-size: 1.125rem;
  color: var(--color-forest-base);
}
.btn-sm {
  padding: 8px 16px;
  font-size: 0.875rem;
}
</style>
