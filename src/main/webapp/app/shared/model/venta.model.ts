import dayjs from 'dayjs';
import { IDispositivos } from 'app/shared/model/dispositivos.model';
import { IOpciones } from 'app/shared/model/opciones.model';
import { IAdicionales } from 'app/shared/model/adicionales.model';

export interface IVenta {
  id?: number;
  precioFinal?: number;
  fechaVenta?: dayjs.Dayjs;
  precioPersonalizaciones?: number;
  precioAdicionales?: number;
  dispositivoVenta?: IDispositivos;
  personalizacionesVentas?: IOpciones[] | null;
  adicionalesVentas?: IAdicionales[] | null;
}

export const defaultValue: Readonly<IVenta> = {};
