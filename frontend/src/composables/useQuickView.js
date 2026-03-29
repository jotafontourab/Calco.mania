import { reactive } from 'vue'

const state = reactive({
  item: null,
  isOpen: false
})

export function useQuickView() {
  const openQuickView = (item) => {
    state.item = item
    state.isOpen = true
  }

  const closeQuickView = () => {
    state.isOpen = false
    // Delay clearing the item to allow for exit transitions
    setTimeout(() => {
        if (!state.isOpen) state.item = null
    }, 300)
  }

  return {
    state,
    openQuickView,
    closeQuickView
  }
}
