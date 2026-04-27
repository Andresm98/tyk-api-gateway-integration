export default async function Home() {

  async function safeFetch(url: string) {
    try {
      const res = await fetch(url, { cache: 'no-store' });
      if (!res.ok) throw new Error('Error en ' + url);
      return await res.json();
    } catch (error) {
      return { message: 'Servicio no disponible', context: 'error' };
    }
  }

  const [dataGo, dataPy, dataClient, dataAccount] = await Promise.all([
    safeFetch('http://tyk-gateway:8080/go-service/health'),
    safeFetch('http://tyk-gateway:8080/python-service/hello'),
    safeFetch('http://tyk-gateway:8080/spring-service-client/health'),
    safeFetch('http://tyk-gateway:8080/spring-service-account/health'),
  ]);

  return (
    <main style={{ padding: '2rem' }}>
      <h1>Arquitectura Distribuida con Tyk</h1>

      <section>
        <div style={{ background: '#e6f4ea', padding: '10px' }}>
          <h3>Servicio Go:</h3>
          <p>{dataGo.service}: {dataGo.status}</p>
        </div>

        <div style={{ background: '#e8f0fe', padding: '10px' }}>
          <h3>Servicio Python:</h3>
          <p>{dataPy.message} ({dataPy.runtime})</p>
        </div>

        <div style={{ background: '#fff3cd', padding: '10px' }}>
          <h3>Spring ms-client:</h3>
          <p>{dataClient.message} ({dataClient.context})</p>
        </div>

        <div style={{ background: '#f8d7da', padding: '10px' }}>
          <h3>Spring ms-account:</h3>
          <p>{dataAccount.message} ({dataAccount.context})</p>
        </div>
      </section>
    </main>
  );
}