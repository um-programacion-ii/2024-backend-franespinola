import { IDispositivos } from 'app/shared/model/dispositivos.model';

export interface ICaracteristicas {
  id?: number;
  nombre?: string;
  descripcion?: string;
  dispositivo?: IDispositivos;
  dispositivoCaracteristicas?: IDispositivos | null;
}

export const defaultValue: Readonly<ICaracteristicas> = {};
