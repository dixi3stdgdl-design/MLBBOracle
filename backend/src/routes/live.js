const clients = new Set();

async function liveRoutes(fastify, options) {
  fastify.get('/live', { websocket: true }, (socket, req) => {
    clients.add(socket);
    console.log(`📡 Live client connected. Total: ${clients.size}`);

    socket.on('message', (message) => {
      try {
        const data = JSON.parse(message);

        if (data.type === 'match_update') {
          const broadcast = {
            type: 'match_update',
            data: {
              hero: data.hero,
              kills: data.kills,
              deaths: data.deaths,
              assists: data.assists,
              gold: data.gold,
              damage: data.damage,
              timestamp: Date.now()
            }
          };

          for (const client of clients) {
            if (client.readyState === 1) {
              client.send(JSON.stringify(broadcast));
            }
          }
        }
      } catch (err) {
        console.error('WebSocket message error:', err);
      }
    });

    socket.on('close', () => {
      clients.delete(socket);
      console.log(`📡 Live client disconnected. Total: ${clients.size}`);
    });

    socket.send(JSON.stringify({
      type: 'connected',
      message: 'Connected to MLBB Oracle live stream'
    }));
  });

  fastify.post('/broadcast', async (request, reply) => {
    const { type, data } = request.body;
    const message = JSON.stringify({ type, data, timestamp: Date.now() });

    let sent = 0;
    for (const client of clients) {
      if (client.readyState === 1) {
        client.send(message);
        sent++;
      }
    }

    return { sent, total: clients.size };
  });
}

module.exports = liveRoutes;
