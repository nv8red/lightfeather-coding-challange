import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
    plugins: [react()],
    server: {
        proxy: {
            '/local': {
                target: "http://host.docker.internal:8080",
                changeOrigin: true,
                secure: false,
                rewrite: path => path.replace('/local', ''),
            }
        },
        cors: false,
        watch: {
            usePolling: true,
        },
        host: "0.0.0.0",
        strictPort: true,
        port: 5173,
    }
})
