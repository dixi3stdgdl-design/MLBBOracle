const fastify = require('fastify')({ logger: true });
const path = require('path');

require('dotenv').config();

fastify.register(require('@fastify/cors'), {
  origin: true,
  methods: ['GET', 'POST', 'PUT', 'DELETE']
});

fastify.register(require('@fastify/websocket'));

fastify.register(require('@fastify/static'), {
  root: path.join(__dirname, '..', 'public'),
  prefix: '/'
});

fastify.register(require('./routes/matches'), { prefix: '/api' });
fastify.register(require('./routes/users'), { prefix: '/api' });
fastify.register(require('./routes/analytics'), { prefix: '/api' });
fastify.register(require('./routes/live'), { prefix: '/ws' });

fastify.get('/', (request, reply) => {
  return reply.sendFile('dashboard/index.html');
});

const start = async () => {
  try {
    const port = process.env.PORT || 3001;
    await fastify.listen({ port, host: '0.0.0.0' });
    console.log(`\n🎮 MLBB Oracle API running on http://localhost:${port}`);
    console.log(`📊 Dashboard: http://localhost:${port}/dashboard/`);
    console.log(`📡 WebSocket: ws://localhost:${port}/ws/live\n`);
  } catch (err) {
    fastify.log.error(err);
    process.exit(1);
  }
};

start();
