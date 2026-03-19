<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps(['activeId'])
const emit = defineEmits(['filter'])

const tribes = ref([])
const loading = ref(true)

const fetchThemes = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/themes')
    const data = await response.json()
    tribes.value = data
  } catch (error) {
    console.error('Error fetching themes:', error)
  } finally {
    loading.value = false
  }
}

const toggleSelection = (id) => {
  if (props.activeId === id) {
    emit('filter', null)
  } else {
    emit('filter', id)
  }
}

onMounted(fetchThemes)
</script>

<template>
  <section class="tribes-section">
    <div class="container">
      <div class="section-header">
        <h2>Explore Nossos Temas</h2>
        <p>Adesivos separados por paixões.</p>
      </div>

      <div v-if="loading" class="loading">Carregando tribos...</div>
      <div v-else class="tribes-grid">
        <div 
          v-for="tribe in tribes" 
          :key="tribe.id" 
          class="tribe-card" 
          :class="{ active: activeId === tribe.id }"
          :style="{ '--tribe-color': tribe.colorHex }"
          @click="toggleSelection(tribe.id)"
        >
          <span class="tribe-icon">{{ tribe.icon }}</span>
          <h3>{{ tribe.name }}</h3>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.tribes-section {
  padding: 80px 0;
  background-color: var(--color-cream-dark);
}
.section-header {
  text-align: center;
  margin-bottom: 48px;
}
.section-header p {
  color: var(--color-forest-base);
  font-size: 1.125rem;
}
.loading {
  text-align: center;
  color: var(--color-forest-light);
  font-style: italic;
}
.tribes-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 24px;
}
.tribe-card {
  background-color: var(--color-white);
  padding: 32px 24px;
  border-radius: var(--radius-md);
  text-align: center;
  box-shadow: var(--shadow-sm);
  transition: all var(--transition-normal);
  cursor: pointer;
  border-top: 4px solid var(--tribe-color);
  opacity: 0.8;
}
.tribe-card.active {
  opacity: 1;
  transform: scale(1.05);
  box-shadow: var(--shadow-lg);
}
.tribe-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-md);
  opacity: 1;
}
.tribe-icon {
  font-size: 2.5rem;
  margin-bottom: 16px;
  display: inline-block;
}
.tribe-card h3 {
  font-family: var(--font-sans);
  font-size: 1.25rem;
  color: var(--color-black);
}
</style>
